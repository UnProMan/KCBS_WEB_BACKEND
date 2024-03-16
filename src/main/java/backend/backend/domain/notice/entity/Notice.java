package backend.backend.domain.notice.entity;

import backend.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "NOTICE_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "NOTICE")
public class Notice {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "NOTICE_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT")
    @Lob
    private String content;

    @Column(name = "WRITE_DATE", nullable = false)
    private LocalDate writeDate;

    /**
     * h2 DB는 TINYINT(1)를 지원 안하기때문에 일단 true, false형식으로 받을 수 있게 변환
     * @TODO 추후에 MySQL로 변경시 다시 변경
     */
    @Column(name = "AUTH")
//    @ColumnDefault("false")
//    @Column(name = "AUTH", columnDefinition = "TINYINT(1)")
    private boolean auth;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    private List<NoticeFiles> files;

}
