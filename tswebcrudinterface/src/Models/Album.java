/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author diego
 */
public class Album implements Serializable{
    private int id;
        private String title;
        private int release_year;

        public Album() {
        }

        public Album(String title, int release_year) {
            this.title = title;
            this.release_year = release_year;
        }

        public Album(int id, String title, int release_year) {
            this.id = id;
            this.title = title;
            this.release_year = release_year;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getRelease_year() {
            return release_year;
        }

        public void setRelease_year(int release_year) {
            this.release_year = release_year;
        }

        @Override
        public String toString() {
            return title + " (" + release_year + ")";
        }
}
