    package namhyun.account_book.dto;

    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;
    import namhyun.account_book.enums.UserType;
    import org.hibernate.validator.constraints.Length;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public class MemberDto extends BaseDto {

        @NotBlank
        @Length(max = 10)
        private String id;

        @NotBlank
        private String name;

        private int age;

        @NotNull
        private UserType userType;
        private String useYn;
    }
