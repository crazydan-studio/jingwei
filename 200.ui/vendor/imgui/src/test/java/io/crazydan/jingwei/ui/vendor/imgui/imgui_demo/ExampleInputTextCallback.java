/*
 * 精卫（JingWei） - 衔木石填沧海，筑屏障护安全
 * Copyright (C) 2025 Crazydan Studio <https://studio.crazydan.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <https://www.gnu.org/licenses/lgpl-3.0.en.html#license-text>.
 */

package io.crazydan.jingwei.ui.vendor.imgui.imgui_demo;

import imgui.ImGui;
import imgui.ImGuiInputTextCallbackData;
import imgui.callback.ImGuiInputTextCallback;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExampleInputTextCallback {
    private static final ImString STR = new ImString();
    private static final StringBuilder OUTPUT = new StringBuilder();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private static final ImGuiInputTextCallback CALLBACK = new ImGuiInputTextCallback() {
        @Override
        public void accept(final ImGuiInputTextCallbackData data) {
            final char c = (char) data.getEventChar();
            if (c == 'h' || c == 'H') {
                data.setEventChar('!');
                OUTPUT.append(DATE_FORMAT.format(LocalDateTime.now())).append(" :: Replaced!\n");
            } else if (c == 'w' || c == 'W') {
                data.setEventChar(0);
                OUTPUT.append(DATE_FORMAT.format(LocalDateTime.now())).append(" :: Discarded!\n");
            } else {
                OUTPUT.append(DATE_FORMAT.format(LocalDateTime.now())).append(" :: Typed: ").append(c).append('\n');
            }
        }
    };

    public static void show(final ImBoolean showInputTextCallback) {
        ImGui.setNextWindowSize(400, 300, ImGuiCond.Once);
        if (ImGui.begin("Input Text Callback Demo", showInputTextCallback)) {
            ImGui.alignTextToFramePadding();
            ImGui.text("Try to input \"Hello World!\":");
            ImGui.sameLine();
            ImGui.inputText("##input", STR, ImGuiInputTextFlags.CallbackCharFilter, CALLBACK);
            ImGui.text(OUTPUT.toString());
        }
        ImGui.end();
    }
}
