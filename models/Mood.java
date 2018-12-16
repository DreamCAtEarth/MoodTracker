package com.poupel.benjamin.moodtracker.models;

import java.util.Date;

/**
 * La classe Mood contient un id général, qui va servir à indiquer sa valeur d'humeur
 * Un id d'icon provenant du layout
 * Un id de couleur provenant des constantes R de couleur
 * Une date de la classe Date
 * Un commentaire
 * Chaque élément sera récupéré ou mis à jour par les getters et les setters lorsqu'il sera nécessaire
 */
public class Mood
{
    private int id;
    private int icon;
    private int color;
    private Date date;
    private String comment;

    public Mood(int id, int icon, int color)
    {
        this.id = id;
        this.icon = icon;
        this.color = color;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
}
