package pl.hajduk;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pl.hajduk.Listeners.ModalListener;
import pl.hajduk.slashCommands.embedded.Zeus;
import pl.hajduk.slashCommands.standardCommands.Clear;
import pl.hajduk.slashCommands.testCommands.Ping;

import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
    {
    public static void main(String[] args) throws Exception
        {
        Random rnd=new Random();
        
        long seed=rnd.nextLong();
        
        rnd.setSeed(seed);
        
        int x=rnd.nextInt(1,667);
        
        String errors="Throwing Errors at line: "+x;
        String komar="hunting mosquitos";
        String acitvityMessage="";
        if(x>200)
            acitvityMessage=errors;
        else
            acitvityMessage=komar;
        
        String token="MTA5NzkzNDM0MzU1MDI3MTYxOA.GHNG1J.sQvaNSJobgmRXFuaE60Pn_uIozDThmDrtnc0CE";
        
        
        //JDA jda= JDABuilder.createDefault(token).setActivity(Activity.playing(acitvityMessage)).build();
        JDA jda=JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing(acitvityMessage)).build().awaitReady();
        
        jda.addEventListener(new Zeus(),new Ping(),new ModalListener(),new Clear());
        
        
        
        
       /* jda.updateCommands().addCommands(
                Commands.slash("say","say something").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS))
        
        ).queue();*/
        
        
        //ConfigureMemoryUsage memoryUsage=new ConfigureMemoryUsage();
        //memoryUsage.configureMemoryUsage( jda);
        
        
        
        }
    }