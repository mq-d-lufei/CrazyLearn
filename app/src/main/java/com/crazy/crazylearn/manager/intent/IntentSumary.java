package com.crazy.crazylearn.manager.intent;

public class IntentSumary {

    /**
     *
     * 地址：https://developer.android.com/guide/components/intents-filters
     *
     *  Intent - Intent-Filter
     *
     *  Intent是一个消息传递对象，可以组件间通信
     *
     *  三个方面：
     *      1、启动Activity
     *          Intent传递给startActivity()/startActivityForResult() 启动新的Activity
     *          Intent描述了要启动的Activity，并可携带数据,通过etResult(0，intent);回传数据
     *      2、启动服务
     *          Intent --> startService
     *          Intent --> bindService
     *          Intent描述了要启动的Service，并可携带数据
     *      3、传递广播
     *          Intent --> sendBroadcast()/sendOrderBroadcast()/sendStickyBroadcast()
     *
     *   一、Intent类型
     *      分为两种类型
     *
     *      1、显示Intent
     *          按名称（完全限定类名）指定要启动的组件。
     *          在自己的应用中使用显式 Intent 来启动组件
     *          创建显式 Intent 启动 Activity 或服务时，系统将立即启动 Intent 对象中指定的应用组件。
     *      2、隐式Intent
     *          不会指定特定的组件，而是声明要执行的常规操作，从而允许其他应用中的组件来处理它
     *          创建隐式 Intent 时，Android 系统通过将 Intent 的内容与在设备上其他应用的清单文件中声明的
     *              Intent 过滤器进行比较，从而找到要启动的相应组件。
     *              如果 Intent 与 Intent 过滤器匹配，则系统将启动该组件，并向其传递 Intent 对象。
     *              如果多个 Intent 过滤器兼容，则系统会显示一个对话框，支持用户选取要使用的应用。
     *
     *      说明：
     *          Intent 过滤器是应用清单文件中的一个表达式，它指定该组件要接收的 Intent 类型。
     *          如果没有为 Activity 声明任何 Intent 过滤器，则 Activity 只能通过显式 Intent 启动。
     *
     *      注意：
     *          为了确保应用的安全性，启动 Service 时，请始终使用显式 Intent， 且不要为服务声明 Intent 过滤器。使用隐式 Intent 启动服务存在安全隐患，
     *          因为您无法确定哪些服务将响应 Intent，且用户无法看到哪些服务已启动。 从 Android 5.0（API 级别 21）开始，如果使用隐式 Intent 调用 bindService()，
     *          系统会引发异常。
     *
     *
     *     二、构建Intent
     *       Intent 中包含的主要信息如下：
     *          1、组件名称
     *              Intent 的这一字段是一个 ComponentName 对象，您可以使用目标组件的完全限定类名指定此对象，其中包括应用的软件包名称。
     *              例如， com.example.ExampleActivity。您可以使用 setComponent()、setClass()、setClassName() 或 Intent 构造函数设置组件名称。
     *
     *          2、操作
     *              ACTION_VIEW
     *                  如果您拥有一些某项 Activity 可向用户显示的信息（例如，要使用图库应用查看的照片；或者要使用地图应用查看的地址）
     *              ACTION_SEND
     *                  这也称为“共享”Intent。如果您拥有一些用户可通过其他应用（例如，电子邮件应用或社交共享应用）共享的数据
     *              自定义操作：
     *                 确保将应用的软件包名称作为前缀， static final String ACTION_TIMETRAVEL = "com.example.action.TIMETRAVEL";
     *              可以使用 setAction() 或 Intent 构造函数为 Intent 指定操作。
     *
     *          3、数据
     *              引用待操作数据和/或该数据 MIME 类型的 URI（Uri 对象）。
     *              创建 Intent 时，除了指定 URI 以外，指定数据类型（其 MIME 类型）往往也很重要。例如，能够显示图像的 Activity 可能无法播放音频文件，即便 URI 格式十分类似时也是如此。因此，指定数据的 MIME 类型有助于 Android 系统找到接收 Intent 的最佳组件。但有时，MIME 类型可以从 URI 中推断得出，特别当数据是 content: URI 时尤其如此。这表明数据位于设备中，且由 ContentProvider 控制，这使得数据 MIME 类型对系统可见。
     *              要仅设置数据 URI，请调用 setData()。 要仅设置 MIME 类型，请调用 setType()。如有必要，您可以使用 setDataAndType() 同时显式设置二者。
     *
     *              注意：
     *                  若要同时设置 URI 和 MIME 类型，请勿调用 setData() 和 setType()，因为它们会互相抵消彼此的值。请始终使用 setDataAndType() 同时设置 URI 和 MIME 类型。
     *
     *          4、类别
     *              附加信息的字符串
     *              CATEGORY_BROWSABLE
     *                  目标 Activity 允许本身通过网络浏览器启动，以显示链接引用的数据，如图像或电子邮件。
     *              CATEGORY_LAUNCHER
     *                  该 Activity 是任务的初始 Activity，在系统的应用启动器中列出
     *
     *              可以使用 addCategory() 指定类别。
     *
     *          说明：
     *              以上列出的这些属性（组件名称、操作、数据和类别）表示 Intent 的既定特征。 通过读取这些属性，Android 系统能够解析应当启动哪个应用组件
     *
     *          5、Extra
     *              携带完成请求操作所需的附加信息的键值对
     *              使用各种 putExtra() 方法添加 extra 数据
     *              使用 putExtras() 将Bundle 插入 Intent 中
     *              声明自己的 extra 键（对于应用接收的 Intent），请确保将应用的软件包名称作为前缀。
     *                 例如： static final String EXTRA_GIGAWATTS = "com.example.EXTRA_GIGAWATTS";
     *
     *          6、标志
     *              在 Intent 类中定义的、充当 Intent 元数据的标志
     *
     *
     *       三、显式 Intent 示例
     *          Intent(Context, Class) 构造函数分别为应用和组件提供 Context 和 Class 对象
     *
     *          Intent downloadIntent = new Intent(this, DownloadService.class);
     *              downloadIntent.setData(Uri.parse(fileUrl));
     *              startService(downloadIntent);
     *
     *      四、隐式 Intent 示例
     *          隐式 Intent 指定能够在可以执行相应操作的设备上调用任何应用的操作。 如果您的应用无法执行该操作而其他应用可以，且您希望用户选取要使用的应用，则使用隐式 Intent 非常有用
     *
     *           // Create the text message with a string
     *            Intent sendIntent = new Intent();
     *           sendIntent.setAction(Intent.ACTION_SEND);
     *            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
     *           sendIntent.setType("text/plain");
     *
     *           // Verify that the intent will resolve to an activity
     *           if (sendIntent.resolveActivity(getPackageManager()) != null) {
     *                startActivity(sendIntent);
     *            }
     *
     *      五、强制使用应用选择器
     *          要显示选择器，请使用 createChooser() 创建 Intent，并将其传递给 startActivity()。例如：
     *
     *          Intent sendIntent = new Intent(Intent.ACTION_SEND);
     *              ...
     *
     *          // Always use string resources for UI text.
     *          // This says something like "Share this photo with"
     *          String title = getResources().getString(R.string.chooser_title);
     *          // Create intent to show the chooser dialog
     *          Intent chooser = Intent.createChooser(sendIntent, title);
     *
     *          // Verify the original intent will resolve to at least one activity
     *          if (sendIntent.resolveActivity(getPackageManager()) != null) {
     *                startActivity(chooser);
     *          }
     *
     *          这将显示一个对话框，其中有响应传递给 createChooser() 方法的 Intent 的应用列表，并且将提供的文本用作对话框标题。
     *
     *
     *        六、接收隐式 Intent
     *           仅当隐式 Intent 可以通过 Intent 过滤器之一传递时，系统才会将该 Intent 传递给应用组件
     *
     *           注：显式 Intent 始终会传递给其目标，无论组件声明的 Intent 过滤器如何均是如此。
     *
     *           注：为了接收隐式 Intent，必须将 CATEGORY_DEFAULT 类别包括在 Intent 过滤器中。 方法 startActivity() 和 startActivityForResult() 将按照已申明 CATEGORY_DEFAULT 类别的方式处理所有 Intent。 如果未在 Intent 过滤器中声明此类别，则隐式 Intent 不会解析为您的 Activity。
     *
     *          <action>   <element>   <category>
     *
     *
     *        七、限制对组件的访问
     *            如果必须确保只有您自己的应用才能启动您的某一组件，请针对该组件将 exported 属性设置为 "false"。
     *
     *            注：对于所有 Activity，您必须在清单文件中声明 Intent 过滤器。但是，广播接收器的过滤器可以通过调用 registerReceiver() 动态注册。 稍后，您可以使用 unregisterReceiver() 注销该接收器。这样一来，应用便可仅在应用运行时的某一指定时间段内侦听特定的广播。
     *
     *
     *        八、过滤器示例
     *              为了更好地了解一些 Intent 过滤器的行为，我们一起来看看从社交共享应用的清单文件中截取的以下片段。
     *
     *               <activity android:name="MainActivity">
     *              <!-- This activity is the main entry, should appear in app launcher -->
     *               <intent-filter>
     *                 <action android:name="android.intent.action.MAIN" />
     *                 <category android:name="android.intent.category.LAUNCHER" />
     *               </intent-filter>
     *              </activity>
     *
     *
     *             <activity android:name="ShareActivity">
     *              <!-- This activity handles "SEND" actions with text element -->
     *               <intent-filter>
     *                  <action android:name="android.intent.action.SEND"/>
     *                  <category android:name="android.intent.category.DEFAULT"/>
     *                   <element android:mimeType="text/plain"/>
     *               </intent-filter>
     *                <!-- This activity also handles "SEND" and "SEND_MULTIPLE" with media element -->
     *                <intent-filter>
     *                  <action android:name="android.intent.action.SEND"/>
     *                   <action android:name="android.intent.action.SEND_MULTIPLE"/>
     *                   <category android:name="android.intent.category.DEFAULT"/>
     *                   <element android:mimeType="application/vnd.google.panorama360+jpg"/>
     *                   <element android:mimeType="image/*"/>
     *                   <element android:mimeType="video/*"/>
     *               </intent-filter>
     *             </activity>
     *
     *
     *         九、使用待定 Intent
     *             PendingIntent 对象是 Intent 对象的包装器。PendingIntent 的主要目的是授权外部应用使用包含的 Intent，就像是它从您应用本身的进程中执行的一样。
     *             待定 Intent 的主要用例包括：
     *                 1、 声明用户使用您的通知执行操作时所要执行的 Intent（Android 系统的 NotificationManager 执行 Intent）。
     *                 2、声明用户使用您的 应用小部件执行操作时要执行的 Intent（主屏幕应用执行 Intent）。
     *                 3、声明未来某一特定时间要执行的 Intent（Android 系统的 AlarmManager 执行 Intent）。
     *
     *             由于每个 Intent 对象均设计为由特定类型的应用组件（Activity、Service 或 BroadcastReceiver）进行处理，因此还必须基于相同的考虑因素创建 PendingIntent
     *             使用待定 Intent 时，应用不会使用调用（如 startActivity()）执行该 Intent。相反，通过调用相应的创建器方法创建 PendingIntent 时，您必须声明所需的组件类型：
     *                 1、PendingIntent.getActivity()，适用于启动 Activity 的 Intent。
     *                 2、PendingIntent.getService()，适用于启动 Service 的 Intent。
     *                 3、PendingIntent.getBroadcast()，适用于启动 BroadcastReceiver 的 Intent。
     *
     *             除非您的应用正在从其他应用中接收待定 Intent，否则上述用于创建 PendingIntent 的方法可能是您所需的唯一 PendingIntent 方法。
     *
     *
     *        十、Intent 解析
     *              根据以下三个方面将该 Intent 与 Intent 过滤器进行比较，搜索该 Intent 的最佳 Activity：
     *              1、Intent 操作（action）
     *              2、Intent 数据（URI 和数据类型）（element）
     *              3、Intent 类别(category)
     *
     *        十一、Intent 匹配
     *              通过 Intent 过滤器匹配 Intent，这不仅有助于发现要激活的目标组件，还有助于发现设备上组件集的相关信息
     *              PackageManager 提供了一整套 query...() 方法来返回所有能够接受特定 Intent 的组件。此外，它还提供了一系列类似的 resolve...() 方法来确定响应 Intent 的最佳组件。
     *              1、queryIntentActivities()
     *              2、queryIntentServices()
     *              3、queryBroadcastReceivers()
     *              4、if (sendIntent.resolveActivity(getPackageManager()) != null) {
     *                      startActivity(sendIntent);
     *                  }
     *
     *
     *        十二、通用 Intent
     *
     *          注意：
     *              如果设备上没有可接收隐式 Intent 的应用，您的应用将在调用 startActivity() 时崩溃。
     *              如需事先验证是否存在可接收 Intent 的应用，请对 Intent 对象调用 resolveActivity()。
     *              如果结果为非空，则至少有一个应用能够处理该 Intent，并且可以安全调用 startActivity()。
     *              如果结果为空，则您不应使用该 Intent。如有可能，您应停用调用该 Intent 的功能。
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
}
