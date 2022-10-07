package com.reborn.reborn.gym.presentation;

import com.reborn.reborn.comment.presentation.dto.CommentResponseDto;
import com.reborn.reborn.gym.application.GymService;
import com.reborn.reborn.gym.domain.Gym;
import com.reborn.reborn.gym.presentation.dto.GymRequestDto;
import com.reborn.reborn.gym.presentation.dto.GymResponseDto;
import com.reborn.reborn.security.domain.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1/gym")
@RestController
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @PostMapping
    public ResponseEntity<Long> createGym(@LoginMember Long memberId, @RequestBody GymRequestDto gymRequestDto){
        Gym gym = gymService.create(memberId, gymRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/gym/" + gym.getId())).body(gym.getId());
    }

    @GetMapping
    public ResponseEntity<List<GymResponseDto>> getGymList(@LoginMember Long memberId){
        List<GymResponseDto> gymList = gymService.getGymList(memberId);
        return ResponseEntity.ok().body(gymList);
    }

    @DeleteMapping("/{gymId}")
    public ResponseEntity<Void> deleteGym(@LoginMember Long memberId,@PathVariable Long gymId){
        gymService.deleteGym(memberId, gymId);
        return ResponseEntity.noContent().build();
    }

}
