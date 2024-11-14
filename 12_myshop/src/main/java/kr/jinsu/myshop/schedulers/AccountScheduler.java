package kr.jinsu.myshop.schedulers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.jinsu.myshop.models.Members;
import kr.jinsu.myshop.services.MembersService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class AccountScheduler {
    /** 업로드 된 파일이 저장될 경로 (application.properties로 부터 읽어옴) */
    // --> import org.springframework.beans.factory.annotation.Value;

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private MembersService membersService;

    //@SCheuled(cron = "0 0 4 * * *") // 매일 새벽 4시에 자동 실행
    @Scheduled(cron = "0 * * * * ?") // 매분 15초마다 실행
    public void processOutMembers() throws InterruptedException {
        log.debug("탈퇴 회원 정리 시작");

        List<Members> outMembers = null;
        try {
            log.debug("탈퇴 회원 조회 및 삭제 시작");
            outMembers = membersService.processOutMembers();
        } catch (Exception e) {
            log.error("탈퇴 회원 조회 및 삭제 실패: " + e);
            return;
        }

        if(outMembers==null || outMembers.size()==0) {
            log.debug("탈퇴 회원이 없습니다.");
            return;
        }

        for(int i = 0; i < outMembers.size(); i++) {
            Members m = outMembers.get(i);
            
            // 사용자가 업로드한 프로필 사진의 실제 경로
            File f = new File(uploadDir + m.getPhoto());
            log.debug("파일 삭제 >>> " + f.getAbsolutePath());

            if (f.exists()) {
                try {
                    f.delete();
                    log.debug("파일 삭제 성공");
                } catch (Exception e) {
                    log.error("파일 삭제 실패: " + e);
                }
            }
        }
    }
}
