package com.backbase.campaignupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backbase.campaignupload.entity.FileApproveEntity;



@Repository
public interface FileApproveRepository extends JpaRepository<FileApproveEntity,Integer> {

}
