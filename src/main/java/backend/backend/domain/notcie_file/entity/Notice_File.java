package backend.backend.domain.notcie_file.entity;

import backend.backend.domain.notice.entity.Notice;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Notice_File {

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

    @Column(name = "FILE_ID")
    @NotNull
    private String file_ID;

}
