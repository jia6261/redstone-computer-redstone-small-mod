# 红石缩小器材方块 GUI 界面草图：微型红石电路配置终端

## 1. 界面概述

*   **界面名称：** 微型红石电路配置终端 (Micro-Redstone Circuit Configurator)
*   **尺寸：** 较大尺寸的 GUI（例如 256x256 像素），以容纳复杂的配置选项。
*   **整体风格：** 科技感、网格化、信息密度高。采用蓝色或红色作为主要高亮色，模拟电路板或全息投影。

## 2. 界面布局与功能分区

| 分区 | 元素名称 | 描述 |
| :--- | :--- | :--- |
| **A. 终端状态区 (顶部)** | **标题** | 居中显示：`Micro-Redstone Circuit Configurator` |
| | **世界状态** | 显示：`STATUS: Connected to Micro-World` 或 `STATUS: Offline` |
| **B. 输入配置区 (左上)** | **输入方块列表** | 3 个输入槽位。玩家可将物品（如红石块、拉杆）放入，以定义输入信号类型。 |
| | **输入方块配置** | 每个槽位旁有按钮，点击打开二级界面，配置输入信号的**外部世界坐标**或**方块类型**。 |
| **C. 输出配置区 (右上)** | **输出方块列表** | 10 个输出槽位。用于定义输出信号的类型和数量。 |
| | **输出方块配置** | 每个槽位旁有按钮，点击打开二级界面，配置输出信号的**外部世界坐标**和**信号强度**。 |
| **D. 文件管理区 (左下)** | **导入按钮** | 按钮文字：`IMPORT (.gmcrs)`。用于从本地文件系统导入电路设计文件。 |
| | **导出按钮** | 按钮文字：`EXPORT (.gmcrs)`。用于将当前微型世界电路导出为文件。 |
| | **文件状态** | 显示：`Last File: Circuit_A.gmcrs` |
| **E. 核心操作区 (右下)** | **进入微型世界** | 按钮文字：`ENTER MICRO-WORLD`。点击后将玩家传送到红石缩小器世界。 |
| | **重置电路** | 按钮文字：`RESET CIRCUIT`。清除微型世界中的所有电路布局。 |

## 3. 核心元素设计细节

### 3.1. 视觉元素

*   **背景：** 采用深蓝色或黑色背景，带有微弱的网格线，模拟电路板。
*   **按钮：** 采用扁平化、科技感强的按钮，悬停时有蓝色或红色光晕。
*   **槽位：** 输入槽位边框为绿色，输出槽位边框为红色，便于区分。

## 4. 视觉稿生成提示 (Prompt for Media Generation)

我将基于上述描述，生成一个 **GUI 界面草图**。

**GUI 提示关键词：** `Minecraft GUI, Micro-Redstone Circuit Configurator, large size, dark blue theme, high-tech, grid lines, Input Slots (green), Output Slots (red), IMPORT/EXPORT buttons, ENTER MICRO-WORLD button, complex configuration, pixel art`
