package pl.hajduk.GuildEvents;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onJoin extends ListenerAdapter
    {
    
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
        {
            String welcomeMsg="Polish version below\n Welcome on SAG-425 discord server if you want to join to our group send us yours application on  ```#application```\n" +
                    ",template you can find on channel above after submiting your application our recruiter will contact you\n" +
                    "POL\n Witaj na serwerze SAG-425 jeżeli chcesz do nas dołączyć najpierw musisz udać się na kanał ```#application``` \n" +
                    "wyślij nam swoje podanie którego wzór znajdziesz na kanale wyżej \n" +
                    "wkrótce potem nasz rekruter się do ciebie odezwie\n" +
                    "if you have any questions contact with Odyn#2209 or Grimm#9950";

        event.getUser().openPrivateChannel().queue((channel)->channel.sendMessage(welcomeMsg).queue());
        }
    }
