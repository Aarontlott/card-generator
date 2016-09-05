/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batch.card.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
 

/**
 *
 * @author Aaron
 */
public class BatchCardGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String path = "D:\\Tabletop Models\\2D stuff\\Cards\\";
        String path_vehicles = "D:\\Tabletop Models\\2D stuff\\tanks\\";
        List<String> vehicle_paths = new ArrayList<>();
        
        try {
            Files.walk(Paths.get(path_vehicles)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    vehicle_paths.add(filePath.getFileName().toString());
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(BatchCardGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println( "Vehicles found: " + vehicle_paths.size());
        // load source images
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path, "blank_template-400x563.png"));
        } catch (IOException ex) {
            Logger.getLogger(BatchCardGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }         
        // END - Loading images, onto the card building

        // create the new image, canvas size is the max. of both image sizes
        int w = 400;
        int h = 563;
        List<Font> card_fonts = new ArrayList<>(); //Add 9
        
        card_fonts.add(new Font("Britannic Bold",Font.BOLD,35)); //Title
        card_fonts.add(new Font("Sans",Font.ITALIC,16)); //Subtitle
        card_fonts.add(new Font("Sans",Font.BOLD,18)); //Key titles
        card_fonts.add(new Font("Sans",Font.PLAIN,18)); //Key info and stats upper
        card_fonts.add(new Font("Sans",Font.PLAIN,13)); //Stats lower
        card_fonts.add(new Font("Sans",Font.BOLD,14)); //Resists title
        card_fonts.add(new Font("Sans",Font.PLAIN,14)); //Resists data
        card_fonts.add(new Font("Sans",Font.BOLD,12)); //Weapons title
        card_fonts.add(new Font("Sans",Font.PLAIN,12)); //Weapons data and bio
         
        // Read json file into string
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\Aaron\\Documents\\NetBeansProjects\\batch-card-generator\\cards.json"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BatchCardGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Gson gson = new GsonBuilder().create();
        
        Machina[] people = gson.fromJson(reader, Machina[].class);
        
//        for (Machina each : people) {
//            System.out.println("Object mode: " + each.bio);
//        }
        Rectangle title_rect = new Rectangle(96,65,192,36);
             
        for (int i = 0; i < vehicle_paths.size(); i++) {
            BufferedImage overlay = null;
            BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            
            try {
                overlay = ImageIO.read(new File(path_vehicles, vehicle_paths.get(i)));
            } catch (IOException ex) {
                Logger.getLogger(BatchCardGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }

            // paint both images, preserving the alpha channels
            Graphics g = combined.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.drawImage(overlay, 208, 99, null); //208, 99 for tanks
            g.setColor(Color.black);
            
            Graphics2D g2 = (Graphics2D) g;
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHints(rh);
            
            // if the tank has data
            for (Machina each : people) {
                if (vehicle_paths.get(i).equals(each.file)) {
                    System.out.println(each.name + " has a match!");
                      g2.setFont(card_fonts.get(0));
                      FontMetrics metrics = g.getFontMetrics(card_fonts.get(0));
                      int x = title_rect.x + (title_rect.width - metrics.stringWidth(each.name)) / 2;
                      int y = (title_rect.y);
                      // Name
                      g2.drawString(each.name, x, y);
                      // Subtitle
                      g2.setFont(card_fonts.get(1));
                      metrics = g.getFontMetrics(card_fonts.get(1));
                      int x2 = title_rect.x + (title_rect.width - metrics.stringWidth(each.type)) / 2;
                      g2.drawString(each.type, x2, y+20);
                      // Key stats
                      g2.setFont(card_fonts.get(2));
                      int keyY = 120;
                      g2.drawString("Armor class:", 30, keyY);
                      g2.drawString("Hit points:", 30, keyY+20);
                      g2.drawString("Speed:", 30, keyY+40);
                      g2.drawString("Hp/ton:", 30, keyY+60);
                      g2.setFont(card_fonts.get(3));
                      g2.drawString(each.armor_class, 150, keyY);
                      g2.drawString(each.hit_points, 130, keyY+20);
                      g2.drawString(each.speed, 100, keyY+40);
                      g2.drawString(each.power_weight, 100, keyY+60);
                      // Stats
                      int statY = 230;
                      g2.drawString("STR", 30, statY);
                      g2.drawString("DEX", 90, statY);
                      g2.drawString("CON", 148, statY);
                      g2.drawString("INT", 215, statY);
                      g2.drawString("WIS", 272, statY);
                      g2.drawString("CHA", 330, statY);
                      // Stats lower
                      g2.setFont(card_fonts.get(4));
                      g2.drawString(each.strength, 30, statY+20);
                      g2.drawString(each.dexterity, 90, statY+20);
                      g2.drawString(each.constitution, 152, statY+20);
                      g2.drawString(each.intelligence, 212, statY+20);
                      g2.drawString(each.wisdom, 272, statY+20);
                      g2.drawString(each.charisma, 335, statY+20);
                      // Resists etc
                      int resistY = 275;
                      g2.setFont(card_fonts.get(5));
                      if (!"".equals(each.dmg_resist)) {
                          g2.drawString("Damage Resistance:", 30, resistY);
                          resistY = resistY + 15;
                      }
                      
                      if (!"".equals(each.dmg_immune)) {
                          g2.drawString("Damage Immunities:", 30, resistY);
                          resistY = resistY + 15;
                      }
                      if (!"".equals(each.condition_immune)) {
                          g2.drawString("Condition Immunities:", 30, resistY);
                          resistY = resistY + 15;
                      }
                      
                      
                      g2.drawString("Senses:", 30, resistY);
                          resistY = resistY + 15;
                      g2.drawString("Languages:", 30, resistY);
                          resistY = resistY + 15;
                      g2.drawString("Challenge:", 30, resistY);
                      g2.setFont(card_fonts.get(6));
                      resistY = 275;
                      if (!"".equals(each.dmg_resist)) { 
                        g2.drawString(each.dmg_resist, 178, resistY);
                        resistY = resistY + 15;
                      }
                      if (!"".equals(each.dmg_immune)) {
                        g2.drawString(each.dmg_immune, 176, resistY);
                        resistY = resistY + 15;
                      }
                      if (!"".equals(each.condition_immune)) {
                        g2.drawString(each.condition_immune, 188, resistY);
                        resistY = resistY + 15;
                      }
                      g2.drawString(each.senses, 90, resistY);
                      resistY = resistY + 15;
                      g2.drawString(each.languages, 118, resistY);
                      resistY = resistY + 15;
                      g2.drawString(each.challenge, 110, resistY);
                      resistY = resistY + 30;
                      // Weapons and hurting stuff
                      int weaponY = resistY;
                      g2.setFont(card_fonts.get(7));
                      int width1 = 0;
                      int width2 = 0;
                      int width3 = 0;
                      if (!"".equals(each.ability_1)) {
                        g2.drawString(each.ability_1, 30, weaponY);
                        width1 = g2.getFontMetrics().stringWidth(each.ability_1);
                      }
                      if (!"".equals(each.ability_2)) {
                        g2.drawString(each.ability_2, 30, weaponY+30);
                        width2 = g2.getFontMetrics().stringWidth(each.ability_2);
                      }
                      if (!"".equals(each.ability_3)) {
                        g2.drawString(each.ability_3, 30, weaponY+60);
                        width3 = g2.getFontMetrics().stringWidth(each.ability_3);
                      }
                      
                      // Bio
                      g2.setFont(card_fonts.get(8));
                      if (!"".equals(each.ability_1)) {
                        drawTextWrap(each.ability_1_d, g2.getFontMetrics(card_fonts.get(7)), g2,35+width1,weaponY,300);
                      }
                      if (!"".equals(each.ability_2)) {
                        drawTextWrap(each.ability_2_d, g2.getFontMetrics(card_fonts.get(7)), g2,35+width2,weaponY+30,300);
                      }
                      if (!"".equals(each.ability_3)) {
                        drawTextWrap(each.ability_3_d, g2.getFontMetrics(card_fonts.get(7)), g2,35+width3,weaponY+60,300);
                      }
                      drawTextWrap(each.bio, g.getFontMetrics(card_fonts.get(8)), g2,58,505,290);
                }
            }
            

            try {
                // Save as new image
                ImageIO.write(combined, "PNG", new File(path + "\\javaDeck\\", "01x " + vehicle_paths.get(i)));
            } catch (IOException ex) {
                Logger.getLogger(BatchCardGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Completed: " + vehicle_paths.get(i) + "- No. " + i + "/" + vehicle_paths.size());
        }
        System.out.println("Thanks!");
        
    }
    
    private static void drawTextWrap(String text, FontMetrics textMetrics, Graphics2D g2, int x, int y, int width)
    {
        int lineHeight = textMetrics.getHeight();
        String textToDraw = text;
        String[] arr = textToDraw.split(" ");
        int nIndex = 0;
        int startX = x;
        int startY = y;
        while ( nIndex < arr.length )
        {
            String line = arr[nIndex++];
            while ( ( nIndex < arr.length ) && (textMetrics.stringWidth(line + " " + arr[nIndex]) < width) )
            {
                line = line + " " + arr[nIndex];
                nIndex++;
            }
            g2.drawString(line, startX, startY);
            startY = startY + lineHeight;
        }
    }
    
    
}
