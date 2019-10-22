package com.lambdaschool.journal.models;

import com.lambdaschool.journal.logging.Loggable;

// minimum data needed to create a user
// allows Spring to convert JSON to java object in OpenController

@Loggable
public class UserMinimum
{
    private String username;
    private String password;
    private String primaryemail;

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

    public String getPrimaryemail()
    {
        return primaryemail;
    }

    public void setPrimaryemail(String primaryemail)
    {
        this.primaryemail = primaryemail;
    }
}