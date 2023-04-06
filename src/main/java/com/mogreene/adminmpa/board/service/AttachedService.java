package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.repository.AttachedRepository;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 자료실 service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttachedService {

    private final BaseRepository baseRepository;
    private final AttachedRepository attachedRepository;
}
