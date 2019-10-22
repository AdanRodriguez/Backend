package com.lambdaschool.journal.models;

import com.lambdaschool.journal.logging.Loggable;

// for manual documentation in SwaggerManualApiPlugin
// used so that custom swagger documentation reflects exactly what is needed to login to the server

@Loggable
public class UserLogin
{
    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}