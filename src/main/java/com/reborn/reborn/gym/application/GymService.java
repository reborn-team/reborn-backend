package com.reborn.reborn.gym.application;

import com.reborn.reborn.gym.domain.Gym;
import com.reborn.reborn.gym.domain.repository.GymRepository;
import com.reborn.reborn.gym.presentation.dto.GymRequestDto;
import com.reborn.reborn.gym.presentation.dto.GymResponseDto;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class GymService {
    private final GymRepository gymRepository;
    private final MemberRepository memberRepository;

    public Gym create(Long memberId, GymRequestDto gymRequestDto) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId.toString()));
        Gym gym = Gym.builder()
                .place(gymRequestDto.getPlace())
                .addr(gymRequestDto.getAddr())
                .lat(gymRequestDto.getLat())
                .lng(gymRequestDto.getLng())
                .member(member)
                .build();
        Gym saveGym = gymRepository.save(gym);

        return saveGym;
    }

    public GymResponseDto.GymList getGymList() {
        return new GymResponseDto.GymList(gymRepository.findAll().stream()
                .map(GymResponseDto::of)
                .collect(Collectors.toList()));
    }

    public void deleteGym(Long authorId, Long gymId) {
        Gym gym = getGym(gymId);
        validIsAuthor(authorId, gym);
        gymRepository.delete(gym);
    }

    private void validIsAuthor(Long authorId, Gym gym) {
        if(!Objects.equals(gym.getMember().getId(), authorId)){
            throw new RuntimeException("권한이 없음");
        }
    }

    public Gym getGym(Long gymId){
        return gymRepository.findById(gymId).orElseThrow();
    }
}
