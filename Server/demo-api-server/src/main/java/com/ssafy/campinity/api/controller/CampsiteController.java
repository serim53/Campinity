package com.ssafy.campinity.api.controller;

import com.ssafy.campinity.api.config.security.jwt.MemberDetails;
import com.ssafy.campinity.api.dto.res.CampsiteLocationInfoDTO;
import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.service.CampsiteService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Api(tags = "캠핑장 관련 API")
@RestController
@RequestMapping("/api/v1/campsites")
@RequiredArgsConstructor
public class CampsiteController {

    private final CampsiteService campsiteService;

    @GetMapping("/scope")
    public ResponseEntity<List<CampsiteLocationInfoDTO>> getCampsiteByScope(
            @RequestParam double topLeftLat,
            @RequestParam double topLeftLng,
            @RequestParam double bottomRightLat,
            @RequestParam double bottomRightLng,
            @RequestParam String doName,
            @RequestParam String siggName) {

        LocationInfoDTO locationInfoDTO = LocationInfoDTO.builder()
                .topLeftLat(topLeftLat)
                .topLeftLng(topLeftLng)
                .bottomRightLat(bottomRightLat)
                .bottomRightLng(bottomRightLng)
                .doName(doName)
                .siggName(siggName)
                .build();

        List<Campsite> result = campsiteService.getCampsitesByLatLng(locationInfoDTO);

        return new ResponseEntity<>(result.stream().map(a -> CampsiteLocationInfoDTO.builder()
                .campsiteId(a.getUuid())
                .lat(a.getLatitude())
                .lng(a.getLongitude()).build()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/conditions")
    public ResponseEntity<List<CampsiteListResDTO>> getCampsiteListByFiltering(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                                               @RequestParam(name = "doName", defaultValue = "") String doName,
                                                                               @RequestParam(name = "sigunguName", defaultValue = "") String sigunguName,
                                                                               @RequestParam(name = "fclty", defaultValue = "") String fclty,
                                                                               @RequestParam(name = "amenity", defaultValue = "") String amenity,
                                                                               @RequestParam(name = "industry", defaultValue = "") String industry,
                                                                               @RequestParam(name = "theme", defaultValue = "") String theme,
                                                                               @RequestParam(name = "allowAnimal", defaultValue = "") String allowAnimal,
                                                                               @RequestParam(name = "openSeason", defaultValue = "") String openSeason,
                                                                               @AuthenticationPrincipal MemberDetails memberDetails) {

        String[] sigunguNames = new String[0];
        if (!sigunguName.trim().isEmpty()) {
            sigunguNames = sigunguName.split(" ");
        }

        String[] fclties = new String[0];
        if (!fclty.trim().isEmpty()) {
            fclties = fclty.split(" ");
        }

        String[] amenities = new String[0];
        if (!amenity.trim().isEmpty()) {
            amenities = amenity.split(" ");
        }

        String[] industries = new String[0];
        if (!industry.trim().isEmpty()) {
            industries = industry.split(" ");
        }

        String[] themes = new String[0];
        if (!theme.trim().isEmpty()) {
            themes = theme.split(" ");
        }


        String[] operSeasons = new String[0];
        if (!openSeason.trim().isEmpty()) {
            operSeasons = openSeason.split(" ");
        }

        String[] allowAnimals = new String[0];
        if (!allowAnimal.trim().isEmpty()) {
            allowAnimals = allowAnimal.split(" ");
        }

        List<CampsiteListResDTO> result = campsiteService.getCampsiteListByFiltering(keyword.trim(), doName.trim(),
                sigunguNames, fclties, amenities, industries, themes, allowAnimals, operSeasons, memberDetails.getMember().getId());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/metadata/{campsiteId}")
    public ResponseEntity<CampsiteListResDTO> getCampsiteMetaData(@PathVariable("campsiteId") UUID campsiteId,
                                                                  @AuthenticationPrincipal MemberDetails memberDetails){
        return new ResponseEntity<>(campsiteService.getCampsiteMetaData(campsiteId, memberDetails.getMember().getId()), HttpStatus.OK);
    }

    @GetMapping("/detail/{campsiteId}")
    public ResponseEntity<CampsiteDetailResDTO> getCampsiteDetail(
            @PathVariable UUID campsiteId,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return new ResponseEntity<>(campsiteService.getCampsiteDetail(campsiteId, memberDetails.getMember().getId()), HttpStatus.OK);
    }

    @PutMapping("/scraps/{campsiteId}")
    public ResponseEntity<IsScrapResDTO> campsiteScrap(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable UUID campsiteId) {

        Boolean isScraped = campsiteService.scrap(memberDetails.getMember().getId(), campsiteId);

        return new ResponseEntity<>(IsScrapResDTO.builder().isScraped(isScraped).build(), HttpStatus.OK);
    }

    @GetMapping("/scrap-lists")
    public ResponseEntity<List<CampsiteMetaResDTO>> getCampsiteScrapList(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<CampsiteMetaResDTO> results = campsiteService.getCampsiteScrapList(memberDetails.getMember().getId());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}