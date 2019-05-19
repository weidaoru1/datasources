package com.datasources.master.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tb_menu_info")
public class MenuInfo implements Serializable {
    @Transient
    private static final long serialVersionUID = 5887591154801776318L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer num;
    private String des;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
}
