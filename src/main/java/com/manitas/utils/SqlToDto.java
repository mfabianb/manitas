package com.manitas.utils;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileReader;

@Log4j2
public class SqlToDto {
    public static void main(String a[]){
        try{
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\mfab\\Documents\\dev\\manitas.sql"));

            String contentLine = br.readLine();
            while (contentLine != null) {
                identifyTable(contentLine);
                identifyFiled(contentLine);
                contentLine = br.readLine();
            }
        }catch (Exception e){
            log.info(e);
        }
    }

    private static void identifyTable(String line){
        if(line.contains("CREATE TABLE IF NOT EXISTS")){
            String[] lines = line.split("\\.");
            String table = lines[1].split(" ")[0];
            log.info("}");
            log.info("");
            log.info("@Table(name=\"" + table + "\")");
            log.info("public class {}Entity {", filedToCamelCase(table, true));
        }
    }

    private static void identifyFiled(String line){
        if(!whichType(line).equals("NO") && !line.contains("CONSTRAINT")){
            String[] lines = line.trim().split(" ");
            log.info("@Column(name=\"" + lines[0] + sizeAnnotation(line) + "\")");
            notNullAnnotation(line);
            log.info("private {} {}", whichType(line), filedToCamelCase(lines[0], false));
            log.info("");
        }
    }

    private static String whichType(String field){

        if(field.contains("TINYINT")){
            return "Boolean";
        }

        if(field.contains("INT")){
            return "Integer";
        }

        if(field.contains("VARCHAR")){
            return "String";
        }

        if(field.contains("DATETIME")){
            return "LocalDateTime";
        }

        return "NO";
    }

    private static String filedToCamelCase(String field, boolean isClass){
        String[] words = field.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for(String word: words){
            stringBuilder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        String newFiled = stringBuilder.substring(0, 1).toLowerCase() + stringBuilder.substring(1);
        if(newFiled.startsWith("id")) newFiled = newFiled.substring(0, 2).toLowerCase() + newFiled.substring(2, 3).toUpperCase() + newFiled.substring(3);
        if(isClass) newFiled = newFiled.substring(0,1).toUpperCase() + newFiled.substring(1);
        return newFiled;
    }

    private static String sizeAnnotation(String field){
        String[] parts = field.split("\\(");
        if(parts.length>1){
            String number = parts[1].split("\\)")[0];
            return (", length=" + number + "");
        }
        return "";
    }

    private static void notNullAnnotation(String field){
        if(field.contains("NOT NULL")) log.info("@NotNull");
    }
}
