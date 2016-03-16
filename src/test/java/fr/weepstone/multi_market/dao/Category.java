package fr.weepstone.multi_market.dao;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by emilie.deltil on 16/03/2016.
 */

@Entity
@Data
@Getter
@EqualsAndHashCode
public class Category {
    private String active;
    private String name;
    private String parent;
    private String root;
    private String description;
    private String meta_title;
    private String meta_keywords;
    private String meta_description;
    private String rewritten;
    private String image;

    public String[] toCsv() {
        String[] line = new String[11];
        line[1] = active;
        line[2] = name;
        line[3] = parent;
        line[4] = root;
        line[5] = description;
        line[6] = meta_title;
        line[7] = meta_keywords;
        line[8] = meta_description;
        line[9] = rewritten;
        line[10] = image;
        return line;
    }
}
