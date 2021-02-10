package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import ru.vote.topjava.model.Menu;
import ru.vote.topjava.model.Voter;
import ru.vote.topjava.repository.VoterRepository;
import ru.vote.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoterService {

    private VoterRepository voterRepository;

    @Autowired
    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    // Достать все положительные результаты голосовании // Достать результаты голосования для конкретного пользователя (Доступно всем)
    public List<Voter> getVoters(Integer id) {
        return voterRepository.getReportForUser(id);
    }

    // Достать результат голосования для конкретного пользователя за конкретный день (Доступно всем)
    public Voter getVoteForUserAboutDay(Integer id, LocalDate date) {
        return voterRepository.getVoterPerDay(id, date);
    }

    // Достать все голоса отданные за конкретное меню (Доступно всем)
    public List<Voter> getVoteByMenuIdAndDate(Integer id, LocalDate date) {
        return voterRepository.getVoteByMenuIdAndDate(id, date);
    }


    public Voter saveVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    public boolean addRecAboutVote(Menu menu, Integer id, boolean voice) throws Exception {
        if (!DateTimeUtil.canEdit(menu.getDate())){
            throw new HttpStatusCodeException(HttpStatus.BAD_REQUEST, "Voting time is up...") {
            };
        }
        try {
            Voter voter = getVoteForUserAboutDay(id, menu.getDate());
            if (voter != null) {
                cancelVoteAndSave(voter);
                if (voter.getMenu().getIdMenu().intValue() == menu.getIdMenu().intValue() && !voice) {
                    return true;
                }
                if (voter.getMenu().getIdMenu().intValue() == menu.getIdMenu().intValue()) {
                    voteAddAndSave(voter, voter.getMenu(), voice);
                    return true;
                }
                voteAddAndSave(voter, menu, voice);
                return true;
            }
            else {
                voter = new Voter(0, menu.getIdMenu(), id, menu.getDate(), false, menu);
                voteAddAndSave(voter, menu, voice);
                return true;
            }
        }
        catch (Exception exc)
        {
            return false;
        }
    }

    private boolean cancelVoteAndSave(Voter voter) {
        if (voter.isVoice()) {
            voter.setVoice(false);
            voter.getMenu().decrementCounter();
            voterRepository.save(voter);
            return true;
        }
        else {
            return true;
        }
    }

    private boolean voteAddAndSave(Voter voter, Menu menu, boolean voice) {
        if (voice) {
            menu.incrementCounter();
        }
        if (!voice) {
            menu.decrementCounter();
        }
        voter.setVoice(voice);
        voter.setMenu(menu);
        voter.setMenuId(menu.getIdMenu());
        voter.setDate(menu.getDate());
        if (voter.getVId() == null) {
            voter.setvId(0);
        }
        voterRepository.save(voter);
        return true;
    }
}
