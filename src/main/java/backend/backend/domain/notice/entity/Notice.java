package backend.backend.domain.notice.entity;

import backend.backend.domain.notcie_file.entity.Notice_File;
import backend.backend.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "TITLE")
    @NotNull
    private String title;

    @Column(name = "CONTENT")
    @Lob
    private String content;

    @Column(name = "WRITE_DATE")
    @NotNull
    private LocalDate write_Date;

    /**
     * h2 DB는 TINYINT(1)를 지원 안하기때문에 일단 true, false형식으로 받을 수 있게 변환
     * 추후에 MySQL로 변경시 다시 변경
     */
    @Column(name = "AUTH")
//    @ColumnDefault("false")
//    @Column(name = "AUTH", columnDefinition = "TINYINT(1)")
    private boolean auth;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    private List<Notice_File> files;

}
