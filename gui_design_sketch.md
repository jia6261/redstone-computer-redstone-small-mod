# 红石电脑方块 GUI 界面草图：红石电脑终端

## 1. 界面概述

*   **界面名称：** 红石电脑终端 (Redstone Computer Terminal)
*   **尺寸：** 标准 Minecraft GUI 尺寸（例如 176x166 像素），背景采用深色、科技感强的纹理。
*   **整体风格：** 借鉴现代科技界面，采用简洁的线条和发光的元素，突出“虚拟机”和“网络连接”的主题。

## 2. 界面布局与功能分区

| 分区 | 元素名称 | 描述 |
| :--- | :--- | :--- |
| **A. 状态显示区 (顶部)** | **终端标题** | 居中显示：`Redstone Computer Terminal` |
| | **电源指示灯** | 一个小圆点，绿色（开机/连接），红色（关机/断开）。 |
| **B. 连接配置区 (左侧)** | **IP 地址输入框** | 默认显示 `127.0.0.1`，玩家可输入目标虚拟机或服务器的 IP 地址。 |
| | **端口输入框** | 默认显示 `25565`，玩家可输入目标端口。 |
| | **连接按钮** | 按钮文字：`CONNECT` / `DISCONNECT`。点击尝试连接或断开。 |
| **C. 核心功能区 (右侧)** | **文件传输按钮** | 按钮文字：`FILE TRANSFER`。点击打开文件管理界面（二级 GUI）。 |
| | **服务器模式开关** | 切换按钮：`SERVER MODE (OFF/ON)`。当拥有足够芯片（或电脑方块）时可激活，用于搭建 MC 内网站。 |
| | **系统重启按钮** | 按钮文字：`REBOOT`。用于重置电脑方块的内部状态。 |
| **D. 实时信息区 (底部)** | **状态日志** | 实时显示连接尝试、文件传输进度、错误信息等。例如：`[INFO] Attempting connection to 192.168.1.10...` |
| | **当前状态** | 显示：`STATUS: Connected (Latency: 5ms)` 或 `STATUS: Offline`。 |

## 3. 核心元素设计细节

### 3.1. 电脑方块贴图 (Computer Block Texture)

*   **风格：** 科技感、工业风。
*   **外观：** 整体为深灰色或黑色金属外壳，侧面有散热孔和电缆接口。
*   **正面：** 占据大部分面积的**屏幕**，屏幕为深蓝色或黑色，在未开机状态下是全黑的。开机后，屏幕上应有微弱的绿色或蓝色光芒，显示终端的启动界面（例如：`OS Booting...`）。
*   **尺寸：** 标准方块贴图（16x16 像素）。

### 3.2. GUI 视觉元素

*   **输入框：** 边框为浅灰色，输入文字为白色或浅绿色。
*   **按钮：** 扁平化设计，未选中时为深灰色，悬停时边框发光（例如蓝色或绿色）。
*   **指示灯：** 采用像素化的 LED 灯效果，突出科技感。

## 4. 视觉稿生成提示 (Prompt for Media Generation)

我将基于上述描述，生成一个 **GUI 界面草图**和**电脑方块贴图**。

**GUI 提示关键词：** `Minecraft GUI, Redstone Computer Terminal, dark theme, pixel art, science fiction, status log, IP address input, CONNECT button, FILE TRANSFER button, green glowing text, 176x166`

**贴图提示关键词：** `Minecraft block texture, 16x16 pixel art, Redstone Computer, dark metal casing, glowing blue screen, industrial design, side vents`
