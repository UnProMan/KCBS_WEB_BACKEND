package backend.backend.domain.notice.entity;

import backend.backend.domain.notice.entity.Notice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "FILES_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "NOTICE_FILES")
public class NoticeFiles {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "FILES_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTICE_ID", referencedColumnName = "ID")
    private Notice notice;

    @Column(name = "FILE_ID", nullable = false)
    private String fileID;

}
