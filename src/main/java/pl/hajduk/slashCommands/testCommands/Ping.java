package pl.hajduk.slashCommands.testCommands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter
    {
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
        {
       String userMessage=event.getMessage().getContentRaw();
       if (userMessage.equalsIgnoreCase("!ping"))
           {
               long currentTime=System.currentTimeMillis();

           event.getChannel().sendMessage("Pinging").queue(msg->{
               long pingTime=System.currentTimeMillis()-currentTime;
               msg.editMessage("ping: "+pingTime+"ms").queue();
           });

           }
       

        }
    
    }
