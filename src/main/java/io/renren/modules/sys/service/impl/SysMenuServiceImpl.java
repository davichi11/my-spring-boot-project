package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Constant;
import io.renren.common.utils.Constant.MenuType;
import io.renren.modules.sys.dao.SysMenuDao;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.SysMenuService;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huchunliang
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        return menuList.stream().filter(menu -> menuIdList.contains(menu.getMenuId())).collect(Collectors.toList());
    }

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId) {
        return sysMenuDao.queryListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return sysMenuDao.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public SysMenuEntity queryObject(Long menuId) {
        return sysMenuDao.queryObject(menuId);
    }

    @Override
    public List<SysMenuEntity> queryList(Map<String, Object> map) {
        return sysMenuDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysMenuDao.queryTotal(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuEntity menu) throws Exception {
        sysMenuDao.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuEntity menu) throws Exception {
        sysMenuDao.update(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] menuIds) throws Exception {
        sysMenuDao.deleteBatch(menuIds);
    }

    @Override
    public List<SysMenuEntity> queryUserList(Long userId) {
        return sysMenuDao.queryUserList(userId);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        //目录
        menuList.forEach(entity -> {
            //目录
            if (entity.getType() == MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        });

        return subMenuList;
    }
}
