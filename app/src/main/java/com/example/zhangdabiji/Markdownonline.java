package com.example.zhangdabiji;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Markdownonline extends AppCompatActivity {
    EditText markdown_edit,markdown_head;
    ProgressDialog progressDialog;
    CheckBox checkBoxispublic;
    String filename="";
    View layout;
    ImageView imageView;
    DrawerLayout drawerLayout;
    TextView scolltext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdownonline);
       drawerLayout=findViewById(R.id.markdown_draw);
        markdown_edit=findViewById(R.id.markdown_edit);
        Button button=findViewById(R.id.markdown_load);
        markdown_head=findViewById(R.id.markdown_headline);
        checkBoxispublic=findViewById(R.id.markdown_ispublic);
        scolltext=findViewById(R.id.main_drawlayout_scoll_text);
        imageView=findViewById(R.id.markdown_help);
        Toolbar toolbar=findViewById(R.id.markdown_toolbar);
        setSupportActionBar(toolbar);
        scolltext.setText("# Heading 1\n" +
                "## Heading 2               \n" +
                "### Heading 3\n" +
                "#### Heading 4\n" +
                "##### Heading 5\n" +
                "###### Heading 6\n" +
                "# Heading 1 link [Heading link](https://github.com/pandao/editor.md \"Heading link\")\n" +
                "## Heading 2 link [Heading link](https://github.com/pandao/editor.md \"Heading link\")\n" +
                "### Heading 3 link [Heading link](https://github.com/pandao/editor.md \"Heading link\")\n" +
                "#### Heading 4 link [Heading link](https://github.com/pandao/editor.md \"Heading link\") Heading link [Heading link](https://github.com/pandao/editor.md \"Heading link\")\n" +
                "##### Heading 5 link [Heading link](https://github.com/pandao/editor.md \"Heading link\")\n" +
                "###### Heading 6 link [Heading link](https://github.com/pandao/editor.md \"Heading link\")\n" +
                "\n" +
                "#### 标题（用底线的形式）Heading (underline)\n" +
                "\n" +
                "This is an H1\n" +
                "=============\n" +
                "\n" +
                "This is an H2\n" +
                "-------------\n" +
                "\n" +
                "### 字符效果和横线等\n" +
                "                \n" +
                "----\n" +
                "\n" +
                "~~删除线~~ <s>删除线（开启识别HTML标签时）</s>\n" +
                "*斜体字*      _斜体字_\n" +
                "**粗体**  __粗体__\n" +
                "***粗斜体*** ___粗斜体___\n" +
                "\n" +
                "上标：X<sub>2</sub>，下标：O<sup>2</sup>\n" +
                "\n" +
                "**缩写(同HTML的abbr标签)**\n" +
                "\n" +
                "> 即更长的单词或短语的缩写形式，前提是开启识别HTML标签时，已默认开启\n" +
                "\n" +
                "The <abbr title=\"Hyper Text Markup Language\">HTML</abbr> specification is maintained by the <abbr title=\"World Wide Web Consortium\">W3C</abbr>.\n" +
                "\n" +
                "### 引用 Blockquotes\n" +
                "\n" +
                "> 引用文本 Blockquotes\n" +
                "\n" +
                "引用的行内混合 Blockquotes\n" +
                "                    \n" +
                "> 引用：如果想要插入空白换行`即<br />标签`，在插入处先键入两个以上的空格然后回车即可，[普通链接](http://localhost/)。\n" +
                "\n" +
                "### 锚点与链接 Links\n" +
                "\n" +
                "[普通链接](http://localhost/)\n" +
                "\n" +
                "[普通链接带标题](http://localhost/ \"普通链接带标题\")\n" +
                "\n" +
                "直接链接：<https://github.com>\n" +
                "\n" +
                "[锚点链接][anchor-id] \n" +
                "\n" +
                "[anchor-id]: http://www.this-anchor-link.com/\n" +
                "\n" +
                "[mailto:test.test@gmail.com](mailto:test.test@gmail.com)\n" +
                "\n" +
                "GFM a-tail link @pandao  邮箱地址自动链接 test.test@gmail.com  www@vip.qq.com\n" +
                "\n" +
                "> @pandao\n" +
                "\n" +
                "### 多语言代码高亮 Codes\n" +
                "\n" +
                "#### 行内代码 Inline code\n" +
                "\n" +
                "执行命令：`npm install marked`\n" +
                "\n" +
                "#### 缩进风格\n" +
                "\n" +
                "即缩进四个空格，也做为实现类似 `<pre>` 预格式化文本 ( Preformatted Text ) 的功能。\n" +
                "\n" +
                "    <?php\n" +
                "        echo \"Hello world!\";\n" +
                "    ?>\n" +
                "    \n" +
                "预格式化文本：\n" +
                "\n" +
                "    | First Header  | Second Header |\n" +
                "    | ------------- | ------------- |\n" +
                "    | Content Cell  | Content Cell  |\n" +
                "    | Content Cell  | Content Cell  |\n" +
                "\n" +
                "#### JS代码　\n" +
                "\n" +
                "```javascript\n" +
                "function test() {\n" +
                "\tconsole.log(\"Hello world!\");\n" +
                "}\n" +
                " \n" +
                "(function(){\n" +
                "    var box = function() {\n" +
                "        return box.fn.init();\n" +
                "    };\n" +
                "\n" +
                "    box.prototype = box.fn = {\n" +
                "        init : function(){\n" +
                "            console.log('box.init()');\n" +
                "\n" +
                "\t\t\treturn this;\n" +
                "        },\n" +
                "\n" +
                "\t\tadd : function(str) {\n" +
                "\t\t\talert(\"add\", str);\n" +
                "\n" +
                "\t\t\treturn this;\n" +
                "\t\t},\n" +
                "\n" +
                "\t\tremove : function(str) {\n" +
                "\t\t\talert(\"remove\", str);\n" +
                "\n" +
                "\t\t\treturn this;\n" +
                "\t\t}\n" +
                "    };\n" +
                "    \n" +
                "    box.fn.init.prototype = box.fn;\n" +
                "    \n" +
                "    window.box =box;\n" +
                "})();\n" +
                "\n" +
                "var testBox = box();\n" +
                "testBox.add(\"jQuery\").remove(\"jQuery\");\n" +
                "```\n" +
                "\n" +
                "#### HTML 代码 HTML codes\n" +
                "\n" +
                "```html\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <mate charest=\"utf-8\" />\n" +
                "        <meta name=\"keywords\" content=\"Editor.md, Markdown, Editor\" />\n" +
                "        <title>Hello world!</title>\n" +
                "        <style type=\"text/css\">\n" +
                "            body{font-size:14px;color:#444;font-family: \"Microsoft Yahei\", Tahoma, \"Hiragino Sans GB\", Arial;background:#fff;}\n" +
                "            ul{list-style: none;}\n" +
                "            img{border:none;vertical-align: middle;}\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1 class=\"text-xxl\">Hello world!</h1>\n" +
                "        <p class=\"text-green\">Plain text</p>\n" +
                "    </body>\n" +
                "</html>\n" +
                "```\n" +
                "\n" +
                "### 图片 Images\n" +
                "\n" +
                "Image:\n" +
                "\n" +
                "![](https://pandao.github.io/editor.md/examples/images/4.jpg)\n" +
                "\n" +
                "> Follow your heart.\n" +
                "\n" +
                "![](https://pandao.github.io/editor.md/examples/images/8.jpg)\n" +
                "\n" +
                "> 图为：厦门白城沙滩\n" +
                "\n" +
                "图片加链接 (Image + Link)：\n" +
                "\n" +
                "[![](https://pandao.github.io/editor.md/examples/images/7.jpg)](https://pandao.github.io/editor.md/images/7.jpg \"李健首张专辑《似水流年》封面\")\n" +
                "\n" +
                "> 图为：李健首张专辑《似水流年》封面\n" +
                "                \n" +
                "----\n" +
                "\n" +
                "### 列表 Lists\n" +
                "\n" +
                "#### 无序列表（减号）Unordered Lists (-)\n" +
                "                \n" +
                "- 列表一\n" +
                "- 列表二\n" +
                "- 列表三\n" +
                "     \n" +
                "#### 无序列表（星号）Unordered Lists (*)\n" +
                "\n" +
                "* 列表一\n" +
                "* 列表二\n" +
                "* 列表三\n" +
                "\n" +
                "#### 无序列表（加号和嵌套）Unordered Lists (+)\n" +
                "                \n" +
                "+ 列表一\n" +
                "+ 列表二\n" +
                "    + 列表二-1\n" +
                "    + 列表二-2\n" +
                "    + 列表二-3\n" +
                "+ 列表三\n" +
                "    * 列表一\n" +
                "    * 列表二\n" +
                "    * 列表三\n" +
                "\n" +
                "#### 有序列表 Ordered Lists (-)\n" +
                "                \n" +
                "1. 第一行\n" +
                "2. 第二行\n" +
                "3. 第三行\n" +
                "\n" +
                "#### GFM task list\n" +
                "\n" +
                "- [x] GFM task list 1\n" +
                "- [x] GFM task list 2\n" +
                "- [ ] GFM task list 3\n" +
                "    - [ ] GFM task list 3-1\n" +
                "    - [ ] GFM task list 3-2\n" +
                "    - [ ] GFM task list 3-3\n" +
                "- [ ] GFM task list 4\n" +
                "    - [ ] GFM task list 4-1\n" +
                "    - [ ] GFM task list 4-2\n" +
                "                \n" +
                "----\n" +
                "                    \n" +
                "### 绘制表格 Tables\n" +
                "\n" +
                "| 项目        | 价格   |  数量  |\n" +
                "| --------   | -----:  | :----:  |\n" +
                "| 计算机      | $1600   |   5     |\n" +
                "| 手机        |   $12   |   12   |\n" +
                "| 管线        |    $1    |  234  |\n" +
                "                    \n" +
                "First Header  | Second Header\n" +
                "------------- | -------------\n" +
                "Content Cell  | Content Cell\n" +
                "Content Cell  | Content Cell \n" +
                "\n" +
                "| First Header  | Second Header |\n" +
                "| ------------- | ------------- |\n" +
                "| Content Cell  | Content Cell  |\n" +
                "| Content Cell  | Content Cell  |\n" +
                "\n" +
                "| Function name | Description                    |\n" +
                "| ------------- | ------------------------------ |\n" +
                "| `help()`      | Display the help window.       |\n" +
                "| `destroy()`   | **Destroy your computer!**     |\n" +
                "\n" +
                "| Left-Aligned  | Center Aligned  | Right Aligned |\n" +
                "| :------------ |:---------------:| -----:|\n" +
                "| col 3 is      | some wordy text | $1600 |\n" +
                "| col 2 is      | centered        |   $12 |\n" +
                "| zebra stripes | are neat        |    $1 |\n" +
                "\n" +
                "| Item      | Value |\n" +
                "| --------- | -----:|\n" +
                "| Computer  | $1600 |\n" +
                "| Phone     |   $12 |\n" +
                "| Pipe      |    $1 |\n" +
                "                \n" +
                "----\n" +
                "\n" +
                "#### 特殊符号 HTML Entities Codes\n" +
                "\n" +
                "&copy; &  &uml; &trade; &iexcl; &pound;\n" +
                "&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot; \n" +
                "\n" +
                "X&sup2; Y&sup3; &frac34; &frac14;  &times;  &divide;   &raquo;\n" +
                "\n" +
                "18&ordm;C  &quot;  &apos;\n" +
                "\n" +
                "[========]\n" +
                "\n" +
                "### Emoji表情 :smiley:\n" +
                "\n" +
                "> Blockquotes :star:\n" +
                "\n" +
                "#### GFM task lists & Emoji & fontAwesome icon emoji & editormd logo emoji :editormd-logo-5x:\n" +
                "\n" +
                "- [x] :smiley: @mentions, :smiley: #refs, [links](), **formatting**, and <del>tags</del> supported :editormd-logo:;\n" +
                "- [x] list syntax required (any unordered or ordered list supported) :editormd-logo-3x:;\n" +
                "- [x] [ ] :smiley: this is a complete item :smiley:;\n" +
                "- [ ] []this is an incomplete item [test link](#) :fa-star: @pandao; \n" +
                "- [ ] [ ]this is an incomplete item :fa-star: :fa-gear:;\n" +
                "    - [ ] :smiley: this is an incomplete item [test link](#) :fa-star: :fa-gear:;\n" +
                "    - [ ] :smiley: this is  :fa-star: :fa-gear: an incomplete item [test link](#);\n" +
                " \n" +
                "#### 反斜杠 Escape\n" +
                "\n" +
                "\\*literal asterisks\\*\n" +
                "\n" +
                "[========]\n" +
                "            \n" +
                "### 科学公式 TeX(KaTeX)\n" +
                "\n" +
                "$$E=mc^2$$\n" +
                "\n" +
                "行内的公式$$E=mc^2$$行内的公式，行内的$$E=mc^2$$公式。\n" +
                "\n" +
                "$$x > y$$\n" +
                "\n" +
                "$$\\(\\sqrt{3x-1}+(1+x)^2\\)$$\n" +
                "                    \n" +
                "$$\\sin(\\alpha)^{\\theta}=\\sum_{i=0}^{n}(x^i + \\cos(f))$$\n" +
                "\n" +
                "多行公式：\n" +
                "\n" +
                "```math\n" +
                "\\displaystyle\n" +
                "\\left( \\sum\\_{k=1}^n a\\_k b\\_k \\right)^2\n" +
                "\\leq\n" +
                "\\left( \\sum\\_{k=1}^n a\\_k^2 \\right)\n" +
                "\\left( \\sum\\_{k=1}^n b\\_k^2 \\right)\n" +
                "```\n" +
                "\n" +
                "```katex\n" +
                "\\displaystyle \n" +
                "    \\frac{1}{\n" +
                "        \\Bigl(\\sqrt{\\phi \\sqrt{5}}-\\phi\\Bigr) e^{\n" +
                "        \\frac25 \\pi}} = 1+\\frac{e^{-2\\pi}} {1+\\frac{e^{-4\\pi}} {\n" +
                "        1+\\frac{e^{-6\\pi}}\n" +
                "        {1+\\frac{e^{-8\\pi}}\n" +
                "         {1+\\cdots} }\n" +
                "        } \n" +
                "    }\n" +
                "```\n" +
                "\n" +
                "```latex\n" +
                "f(x) = \\int_{-\\infty}^\\infty\n" +
                "    \\hat f(\\xi)\\,e^{2 \\pi i \\xi x}\n" +
                "    \\,d\\xi\n" +
                "```\n" +
                "\n" +
                "### 分页符 Page break\n" +
                "\n" +
                "> Print Test: Ctrl + P\n" +
                "\n" +
                "[========]\n" +
                "\n" +
                "### 绘制流程图 Flowchart\n" +
                "\n" +
                "```flow\n" +
                "st=>start: 用户登陆\n" +
                "op=>operation: 登陆操作\n" +
                "cond=>condition: 登陆成功 Yes or No?\n" +
                "e=>end: 进入后台\n" +
                "\n" +
                "st->op->cond\n" +
                "cond(yes)->e\n" +
                "cond(no)->op\n" +
                "```\n" +
                "\n" +
                "[========]\n" +
                "                    \n" +
                "### 绘制序列图 Sequence Diagram\n" +
                "                    \n" +
                "```seq\n" +
                "Andrew->China: Says Hello \n" +
                "Note right of China: China thinks\\nabout it \n" +
                "China-->Andrew: How are you? \n" +
                "Andrew->>China: I am good thanks!\n" +
                "```\n" +
                "\n" +
                "### End");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (!drawerLayout.isDrawerOpen(GravityCompat.END))
                  drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        final File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/tencent/QQfile_recv", "test.txt");
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!file.exists()) {
                   try {
                       file.createNewFile();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
               if (file.exists()){
                   try(FileWriter writer=new FileWriter(file)) {
                       String s=markdown_edit.getText().toString();
                       writer.write(s);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
               else {
                   return;
               }
               if (!mainView.preferences.getBoolean("thisweekisload",false)){

                   // Log.d(TAG, "onClick: "+MainActivity.preferences.getBoolean("thisweekisload",false));
                   if (markdown_head.getText().toString()!=""||markdown_edit.getText().toString()!="")
                       filename=markdown_head.getText().toString();
//                   for(int i=0;i<filename.length();i++){
//                       if (filename.toCharArray()[i]=='.'){
//                           x=filename.substring(0,i);
//                           break;
//                       }
//                   }
                   else {
                       Toast.makeText(Markdownonline.this, "标题或内容为空", Toast.LENGTH_SHORT).show();
                       return;}
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           boolean ispublic;
                           ispublic=checkBoxispublic .isChecked();
                           if (!Markdownonline. this.isFinishing()){
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       if (progressDialog==null)
                                           progressDialog=new ProgressDialog(Markdownonline.this);
                                       ProgressShow.showProgressDialog(progressDialog);
                                       progressDialog.show();
                                   }
                               });
                           }
                           if (mainView.preferences.getBoolean("openPrivacy",false)){
                               ispublic=false;
                           }

                           String ispub=String.valueOf(ispublic);
                           char a[]=ispub.toCharArray();
                           a[0]-=32;
                           ispub=String.copyValueOf(a);
                           OkHttpClient client=new OkHttpClient();
                           RequestBody requestBody = new MultipartBody.Builder()
                                   .setType(MultipartBody.FORM)

                                   .addFormDataPart("file", filename+".md",
                                           RequestBody.create(MediaType.parse("multipart/form-data"),file ))
                                   .addFormDataPart("is_public", String.valueOf(ispub))
                                   .addFormDataPart("file_type","md")
                                   .addFormDataPart("title",markdown_head.getText().toString())
                                   .build();//传文件

                           Request request = new Request.Builder()
                                   .header("Authorization", "Client-ID " + UUID.randomUUID())
                                   .url("http://47.103.205.169/api/summary/?token="+MainActivity.token)
                                   .post(requestBody)
                                   .build();
                           client.newCall(request).enqueue(new Callback() {
                               @Override
                               public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                   runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           Toast.makeText(Markdownonline.this, "上传失败", Toast.LENGTH_SHORT).show();
                                       }
                                   });
                               }

                               @Override
                               public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                   runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           progressDialog.dismiss();
                                           Toast.makeText(Markdownonline.this, "上传成功", Toast.LENGTH_SHORT).show();
                                           mainView.editor.putBoolean("thisweekisload",true);
                                           int count=Integer.parseInt(mainView.preferences.getString("userloadcount","0"));
                                           count++;
                                           mainView.editor.putString("userloadcount",String.valueOf(count));
                                           mainView.editor.apply();

                                       }
                                   });
                               }
                           });
                       }
                   }).start();
               }
               else Toast.makeText(Markdownonline.this, "本周已上传", Toast.LENGTH_SHORT).show();

           }
       });
       layout=drawerLayout.getChildAt(1);
        ViewGroup.LayoutParams lp=layout.getLayoutParams();
        lp.width=getResources().getDisplayMetrics().widthPixels;
       layout.setLayoutParams(lp);
       drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
           @Override
           public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
               if (!drawerLayout.isDrawerOpen(GravityCompat.END))
                   layout.setClickable(true);
           }

           @Override
           public void onDrawerOpened(@NonNull View drawerView) {

           }

           @Override
           public void onDrawerClosed(@NonNull View drawerView) {

           }

           @Override
           public void onDrawerStateChanged(int newState) {

           }
       });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (drawerLayout.isDrawerOpen(GravityCompat.END))
                drawerLayout.closeDrawer(GravityCompat.END);
            else finish();
        }
        return false;
    }
}
