package com.darm.ifce.achadoseperdidos.model;

import lombok.*;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import com.darm.ifce.achadoseperdidos.model.enums.TypeClass;
import com.darm.ifce.achadoseperdidos.model.enums.TypeUser;

@Entity
@Table(name = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String name;

    @Column 
    private String email;

    @Column 
    private String phone;

    @Column
    private TypeUser typeUser;

    @Column 
    private TypeClass typeClass;

    public String getFirtsName() {
        if (name != null) {
            return name.split(" ")[0];
        }
        return "";
    }

    public String getLastName() {
        if (name != null) {
            String[] separateNameBySpace = name.split(" ");

            return separateNameBySpace[separateNameBySpace.length - 1];
        }
        return "";
    }
}
