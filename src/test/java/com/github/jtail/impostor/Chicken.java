package com.github.jtail.impostor;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class Chicken {
    private String wing;

    @Column(name = "nobrain")
    private Boolean head;
}
