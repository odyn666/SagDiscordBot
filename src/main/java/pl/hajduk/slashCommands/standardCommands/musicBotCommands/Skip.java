package pl.hajduk.slashCommands.standardCommands.musicBotCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import pl.hajduk.service.audio.musicBot.PlayerManager;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.awt.*;
import java.util.List;

public class Skip implements ICommand {
    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getDescription() {
        return "skip current song";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING, getName(), getDescription()));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        PlayerManager playerManager = PlayerManager.get();
        OptionMapping msg = event.getOption("count");

        int count = 1;
        if (msg != null) {
            count = event.getOption("count").getAsInt();
            if (count > 10) {
                event.replyEmbeds(new EmbedBuilder()
                        .setDescription("skipping songs must be less than 10")
                        .setColor(Color.RED)
                        .build()
                ).queue();
            }
        }

        for (int i = 0; i < count; i++) {
            playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().getPlayer().stopTrack();

        }
        AudioTrack playingTrack=playerManager.getGuildMusicManager(event.getGuild()).getPlayer().getPlayingTrack();
        if (playingTrack != null) {
            event.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.green)
                    .setDescription("currently playing: "+playingTrack.getInfo().title)
                    .build()
            ).queue();
        }




    }
}
