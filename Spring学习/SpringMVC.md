1.pojo类
用于存放数据库内的实体
2.Mapper
是一个接口用于查询（可用代理实例化），对应Mybatis中的字段
3.Service接口
服务类，用于处理Mapper取出数据库，做对应的服务。
4.ServiceImp类
用于实现服务接口的类
5.Controller
用于连接网页，用@RequestMapping（""）连接到前端，获取httpsession的数据。一般是获取放入Model类，然后用Service类处理后的数据传输到Model中。
```
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
  
    @RequestMapping("admin_category_list")
    public String list(Model model){
        List<Category> cs= categoryService.list();
        model.addAttribute("cs", cs);
        return "admin/listCategory";
    }
}
```


# 关于SpringMVC的配置文件
```

```