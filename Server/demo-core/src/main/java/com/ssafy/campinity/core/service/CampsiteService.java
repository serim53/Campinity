package com.ssafy.campinity.core.service;

import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CampsiteService {
    List<Campsite> getCampsitesByLatLng(LocationInfoDTO locationInfoDTO);

    List<CampsiteListResDTO> getCampsiteListByFiltering(String keyword, String doName, String sigunguName,
                                                        String[] fclties, String[] amenities, String[] induties,
                                                        String[] themas, String[] allowAnimals, String[] operSeasons, UUID userId);

    CampsiteMetaResDTO getCampsiteMetaData(UUID campsiteId);

    void scrap(UUID userId, UUID campsiteId);

    CampsiteDetailResDTO getCampsiteDetail(UUID campsiteId, UUID userId);
}
