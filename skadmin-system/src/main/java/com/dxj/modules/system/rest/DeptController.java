package com.dxj.modules.system.rest;

import com.dxj.aop.log.Log;
import com.dxj.config.DataScope;
import com.dxj.enums.EntityEnums;
import com.dxj.exception.BadRequestException;
import com.dxj.modules.system.domain.Dept;
import com.dxj.modules.system.service.DeptService;
import com.dxj.modules.system.dto.DeptDTO;
import com.dxj.modules.system.query.DeptQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author dxj
 * @date 2019-03-25
 */
@RestController
@RequestMapping("api")
public class DeptController {

    private final DeptService deptService;

    private final DeptQueryService deptQueryService;

    private final DataScope dataScope;

    @Autowired
    public DeptController(DeptService deptService, DeptQueryService deptQueryService, DataScope dataScope) {
        this.deptService = deptService;
        this.deptQueryService = deptQueryService;
        this.dataScope = dataScope;
    }

    @Log("查询部门")
    @GetMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity<Object> getDept(DeptDTO resources) {
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();
        List<DeptDTO> deptDTOS = deptQueryService.queryAll(resources, deptIds);
        return new ResponseEntity<>(deptService.buildTree(deptDTOS), HttpStatus.OK);
    }

    @Log("新增部门")
    @PostMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_CREATE')")
    public ResponseEntity<DeptDTO> create(@Validated @RequestBody Dept resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + EntityEnums.DEPT_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(deptService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改部门")
    @PutMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_EDIT')")
    public ResponseEntity<Void> update(@Validated(Dept.Update.class) @RequestBody Dept resources) {
        deptService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @DeleteMapping(value = "/dept/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deptService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
