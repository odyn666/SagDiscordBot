package pl.hajduk.GuildEvents;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onJoin extends ListenerAdapter
    {
    
    @Override
    public void onGuildJoin(GuildJoinEvent event)
        {
        super.onGuildJoin(event);
        }
    }
