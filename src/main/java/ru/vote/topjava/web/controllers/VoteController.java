package ru.vote.topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.vote.topjava.model.Menu;
import ru.vote.topjava.model.User;
import ru.vote.topjava.model.Voter;
import ru.vote.topjava.service.VoterService;
import ru.vote.topjava.util.SecurityUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String VOTE_URL = "/rest/vote";

    @Autowired
    private VoterService voterService;

    // Достать все положительные результаты голосования для конкретного пользователя (Доступно только хозяину  голосов)
    @RequestMapping(value = "/get_vote", method = RequestMethod.GET)
    public List<Voter> getVote(){
        return voterService.getVoters((int) SecurityUtil.authUserId());
    }

    // Достать все положительные голоса отданные за конкретное меню (Доступно всем)
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public List<Voter> getVoteForMenu(@RequestParam(name = "menu_id", required = true) Integer id,
                                      @RequestParam(name = "date", required = true)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return voterService.getVoteByMenuIdAndDate(id, date);
    }

    // Достать результат голосования для конкретного пользователя за конкретный день (Доступно всем)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Voter getVoteForUser(@RequestParam(name = "user_id", required = true) Integer id,
                                @RequestParam(name = "date", required = true)
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return voterService.getVoteForUserAboutDay(id, date);
    }

    // Добавить или обновить запись о голосовании за меню (Доступно только хозяину голоса)
    @RequestMapping(value = "/update/{voice}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createOrUpdate(@RequestBody Menu menu,
                               @PathVariable(name = "voice", required = true) boolean voice) throws SQLException {
        voterService.addRecAboutVote(menu, (int) SecurityUtil.authUserId(), voice);
    }
}
