
import java.io.File;
import java.util.ArrayList;

public class fileSearch {
    public static ArrayList<String> fileSearch(String context, ArrayList<String> stringArrayList) {
        //获得系统盘符
        File[] roots = File.listRoots();
        int n = roots.length;
        //不能用roots直接执行for循环,因为会产生内部行,可以扩展写内部行产生的原因以及解决的方法
        //测试程序时，使条件为n不等于1，达到不遍历c盘的目的，减少程序运行的时间
        //提交程序时，条件为n不等于0，遍历所有盘符
        while (n!=1){
            findFile(roots[n-1], context, stringArrayList);
            n--;
        }
        return stringArrayList;
    }

    //创建递归方法搜索文件夹
    public static void findFile(File src, String context, ArrayList<String> list) {
        if (context.equals("")) list.add("请输入查询条件");
        else {
            //进入文件夹
            File[] files = src.listFiles();
            //由于存在空文件夹或需要权限才能进入的文件夹，所以要对文件夹进行判断
            if (files != null) {
                //遍历数组，依次得到src里面的每一个文件或者文件夹
                for (File file : files) {
                    //进行判断，如果是文件，就执行相关的查找要求
                    if (file.isFile()) {
                        String name = file.getName();
                        //检验文件是否包含输入的搜索信息
                        if (name.contains(context)) {
                            list.add(file.toString());
                        }
                    }
                    //进行判断，如果是文件夹，就进行递归
                    else {
                        //此时的file是方法调用者的次一级路径
                        findFile(file, context, list);
                    }
                }
            }
        }
    }
}
