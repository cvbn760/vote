package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Menu;
import ru.vote.topjava.model.User;
import ru.vote.topjava.model.Voter;
import ru.vote.topjava.repository.VoterRepository;
import ru.vote.topjava.util.DateTimeUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class VoterService {

    private VoterRepository voterRepository;

    @Autowired
    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    // Достать все положительные результаты голосовании // Достать результаты голосования для конкретного пользователя
    public List<Voter> getVoters(Integer id) {
        return (id == null) ? voterRepository.findAll() : voterRepository.getReportForUser(id);
    }

    // Достать результат голосования для конкретного пользователя за конкретный день
    public Voter getVoteForUserAboutDay(Integer id, LocalDate date) {
        return voterRepository.getVoterPerDay(id, date);
    }

    // Достать все голоса отданные за конкретное меню
    public List<Voter> getVoteByMenuIdAndDate(Integer id, LocalDate date) {
        return voterRepository.getVoteByMenuIdAndDate(id, date);
    }

    // Сохранить голос
    public Voter saveVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    // Добавить или обновить запись о голосовании за меню
    @Transactional
    public boolean addRecAboutVote(Menu menu, Integer id, boolean voice) throws SQLException {
        try {
            Voter voter = getVoteForUserAboutDay(id, menu.getDate());
            // Пользователь уже голосовал в текущую дату
            if (voter != null && DateTimeUtil.canVote(voter.getDate(), menu.getDate())) {
                // 1) Отменяем уже отданный голос
                cancelVoteAndSave(voter);
                // Если нужно только отменить
                if (voter.getMenu().getIdMenu().intValue() == menu.getIdMenu().intValue() && !voice) {
                    return true;
                }
                // 2) Голосуем заново
                if (voter.getMenu().getIdMenu().intValue() == menu.getIdMenu().intValue()) {
                    voteAddAndSave(voter, voter.getMenu(), voice);
                    return true;
                }
                voteAddAndSave(voter, menu, voice);
                return true;
            }
            else { // Пользователь еще не голосовал в текущую дату
                // 1) Голосуем
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

    // Отменить голос и сохранить отмену
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

    // Присвоить голос и сохранить изменения
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
