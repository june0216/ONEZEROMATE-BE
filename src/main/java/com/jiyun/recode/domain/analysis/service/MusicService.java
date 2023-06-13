package com.jiyun.recode.domain.analysis.service;

import com.jiyun.recode.domain.analysis.domain.MusicRecommendation;
import com.jiyun.recode.domain.analysis.dto.MusicListResDto;
import com.jiyun.recode.domain.analysis.repository.MusicRepository;
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
}
