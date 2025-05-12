package com.yc.snackoverflow.controller;

import com.yc.snackoverflow.data.MemberDto;
import com.yc.snackoverflow.enums.UpsertStatusEnum;
import com.yc.snackoverflow.handler.ResultData;
import com.yc.snackoverflow.model.Member;
import com.yc.snackoverflow.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "Member Management", description = "API endpoints for member management")
public class MemberController {


    private final MemberService memberService;

    /**
     * Create a new member
     */
    @Operation(summary = "Create member", description = "Create a new member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResultData<UpsertStatusEnum> createMember(@Validated @RequestBody MemberDto memberDto) {
        log.info("Creating new member: {}", memberDto.getName());
        UpsertStatusEnum status = memberService.createOrUpdate(memberDto);
        return ResultData.success("Member created successfully", status);
    }

    /**
     * Update an existing member
     */
    @Operation(summary = "Update member", description = "Update an existing member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{name}")
    public ResultData<UpsertStatusEnum> updateMember(
            @PathVariable String name,
            @Validated @RequestBody MemberDto memberDto) {
        log.info("Updating member: {}", name);
        memberDto.setName(name); // Ensure name matches path variable
        UpsertStatusEnum status = memberService.createOrUpdate(memberDto);
        return ResultData.success("Member updated successfully", status);
    }

    /**
     * Get members list with optional filtering
     */
    @Operation(summary = "Get members", description = "Get a list of members with optional filtering by names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResultData<List<Member>> getMembers(
            @Parameter(description = "List of member names for filtering")
            @RequestParam(required = false) List<String> memberNameList) {
        log.info("Getting members list, filter by names: {}", memberNameList);
        List<Member> members = memberService.list(memberNameList);
        return ResultData.success(members);
    }

    /**
     * Get a member by name
     */
    @Operation(summary = "Get member by name", description = "Get a specific member by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{name}")
    public ResultData<Member> getMemberByName(
            @Parameter(description = "Member name")
            @PathVariable String name) {
        log.info("Getting member by name: {}", name);
        List<Member> members = memberService.list(List.of(name));
        return ResultData.success(members.get(0));
    }

    /**
     * Delete a member (logical deletion)
     */
    @Operation(summary = "Delete member", description = "Delete a member by name (logical deletion)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResultData<Void> deleteMember(
            @Parameter(description = "Member name")
            @PathVariable String name) {
        log.info("Deleting member: {}", name);
        MemberDto memberDto = new MemberDto();
        memberDto.setName(name);
        memberDto.setAlive(false); // Logical deletion
        memberService.createOrUpdate(memberDto);
        return ResultData.success("Member deleted successfully", null);
    }
}
