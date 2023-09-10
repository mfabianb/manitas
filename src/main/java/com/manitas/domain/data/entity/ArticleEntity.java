package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name="article")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class ArticleEntity implements Serializable {

    @Id
    @Column(name="id_article")
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idArticle;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="description")
    @NotNull
    private String description;

    @Column(name="info")
    @NotNull
    private String info;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_media", referencedColumnName = "id_media")
    private MediaEntity idMedia;

    @Column(name="creation_date")
    @NotNull
    private LocalDateTime creationDate;

    @Column(name="modification_date")
    private LocalDateTime modificationDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private UserEntity idUser;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_topic", referencedColumnName = "id_topic")
    private TopicEntity idTopic;

    @Column(name="enable")
    @NotNull
    private Boolean enable;
}
