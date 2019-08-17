# svgfont
SVG图片与字体格式转换

# 从SVG格式的字体文件中导出SVG图标

代码：
```
File fontFile=new File(".iconfont.svg");
File outputDir = new File("./svgoutput");
FontParser.parse(fontFile, outputDir);
```
输出目录下：
```
总用量 20K
drwxr-xr-x 2 chengda chengda 4.0K  8月 17 21:22 .
drwxr-xr-x 3 chengda chengda 4.0K  8月 17 21:22 ..
-rw-r--r-- 1 chengda chengda 2.1K  8月 17 21:22 collection.svg
-rw-r--r-- 1 chengda chengda 1.8K  8月 17 21:22 customer.svg
-rw-r--r-- 1 chengda chengda 1.3K  8月 17 21:22 logout.svg

```
