package com.jiyun.recode.domain.analysis.service;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.analysis.domain.MusicRecommendation;
import com.jiyun.recode.domain.analysis.dto.MusicListResDto;
import com.jiyun.recode.domain.analysis.dto.MusicRecommendProfileUpdateReqDto;
import com.jiyun.recode.domain.analysis.repository.MusicRepository;
import com.jiyun.recode.domain.diary.domain.Answer;
import com.jiyun.recode.domain.diary.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MusicService {
	private final MusicRepository musicRepository;

	public void uploadMusic(Post post, List<MusicListResDto.MusicResDto> musicList){
		for(MusicListResDto.MusicResDto music : musicList){
			MusicRecommendation musicRecommendation = MusicRecommendation.builder()
					.artist(music.getArtist())
					.track(music.getTrack())
					.url(music.getUrl())
					.build();
			musicRepository.save(musicRecommendation);
			post.setMusic(musicRecommendation);
		}

	}

	public MusicRecommendProfileUpdateReqDto updateUserProfile(Account account, Post post, Integer moodNum){
		String musicContent = "Hype Boy-NewJeans";
		for(Answer answer : post.getAnswers()){
			if(answer.getQuestion().getQuestionId() == 2){
				musicContent = answer.getContent();
				break;
			}
		}
		if (musicContent.contains("-")) {
			String[] parts = musicContent.split("-");

		} else {
			musicContent = "Hype Boy-NewJeans";
		}

		String[] parts = musicContent.split("-");

		String track = parts[0]; // "word1"
		String artist = parts[1]; // "word2"

			MusicRecommendProfileUpdateReqDto musicRecommendProfileUpdateReqDto =
					MusicRecommendProfileUpdateReqDto.builder()
							.uuid(account.getAccountId())
							.artist(artist)
							.moodId(moodNum)
							.track(track)
							.uuid(account.getAccountId())
							.build();
			return musicRecommendProfileUpdateReqDto;


	}
}
