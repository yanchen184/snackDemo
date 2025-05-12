package com.yc.snackoverflow.model;

import com.yc.snackoverflow.enums.MemberActionEnum;
import com.yc.snackoverflow.model.baseAbstract.BaseLongIdDO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOGIN_LOG")
public class MemberLog extends BaseLongIdDO {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Member member;

    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    private MemberActionEnum memberAction;
}
