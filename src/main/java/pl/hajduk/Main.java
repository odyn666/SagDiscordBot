package pl.hajduk;


import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import pl.hajduk.GuildEvents.onJoin;
import pl.hajduk.Listeners.ModalListener;
import pl.hajduk.config.FromJson;
import pl.hajduk.service.CommandManager;
import pl.hajduk.slashCommands.standardCommands.musicBotCommands.*;
import pl.hajduk.slashCommands.standardCommands.sagCommands.Zeus;
import pl.hajduk.slashCommands.standardCommands.ttsCommands.Voice;
import pl.hajduk.slashCommands.testCommands.Ping;

import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    public static void main(String[] args) throws Exception {
       // TwatListener twatListener = new TwatListener();
        CommandManager manager = new CommandManager();


        FromJson fJ = new FromJson();
        fJ.setPrefix("!");
        Random rnd = new Random();
        System.out.println(fJ.getPrefix());
        long seed = rnd.nextLong();

        rnd.setSeed(seed);

        int x = rnd.nextInt(1, 667);
        Boolean fuckedUP = false;
        String errors = "Throwing Errors at line: " + x;
        String komar = "hunting mosquitos";
        String acitvityMessage = "";
        if (x > 200) {
            acitvityMessage = errors;
        } else {
            acitvityMessage = komar;
        }
        JDABuilder builder = JDABuilder.create(fJ.getToken(), GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.SCHEDULED_EVENTS, GatewayIntent.GUILD_PRESENCES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);


        if (fuckedUP) {
            builder.setActivity(Activity.playing("ALPHA BUILD"));
            acitvityMessage = "guys you fucked up badly";
        }
//TODO: remove old implementation of addEventListeners
//TODO: add playing already downloaded playlists
        builder.addEventListeners(new Zeus(), new Ping(), new ModalListener(), new onJoin());
        manager.add(new Voice());
        manager.add(new Play());
        manager.add(new Clear());
        manager.add(new Pause());
        manager.add(new Resume());
        manager.add(new Skip());
        manager.add(new Queue());
        manager.add(new Delete());
        manager.add(new AddAllPlaylistsToQueue());


        builder.addEventListeners(manager);
        builder.setEventPassthrough(true);
        builder.build().awaitReady();

//        EbWj349eIDwQPskBRg_IMKCk2Ds4kVEg  kutang secret

    }


}