/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batch.card.generator;

import java.util.List;

/**
 *
 * @author Aaron
 */
public class Machina {
    public Machina(String name) {
        this.name = name;
    }
    public String file;   
    public String name;
    public String type;
    public String armor_class;
    public String hit_points;
    public String speed;
    public String power_weight;
    public String strength;
    public String dexterity;
    public String constitution;
    public String intelligence;
    public String wisdom;
    public String charisma;
    public String dmg_resist;
    public String dmg_immune;
    public String condition_immune;
    public String senses;
    public String languages;
    public String challenge;
    public String ability_1;
    public String ability_1_d;
    public String ability_2;
    public String ability_2_d;
    public String ability_3;
    public String ability_3_d;
    public String bio;
    
    
    
    @Override
    public String toString(){
      return "Name: "+this.name+"n" +
        "File: "+this.file+"n";
    } 

}

class Arma {
    public String name;
    public String type;
    public String armor_class;
    public String hit_points;
    public String speed;
    public String power_weight;
    public String strength;
    public String dexterity;
    public String constitution;
    public String intelligence;
    public String wisdom;
    public String charisma;
    public String dmg_resist;
    public String dmg_immune;
    public String condition_immune;
    public String senses;
    public String languages;
    public String challenge;
    public String ranged;
    public String ranged_desc;
    public String ranged_hit;
    public String melee;
    public String melee_desc;
    public String melee_hit;
    public String bio;
}
