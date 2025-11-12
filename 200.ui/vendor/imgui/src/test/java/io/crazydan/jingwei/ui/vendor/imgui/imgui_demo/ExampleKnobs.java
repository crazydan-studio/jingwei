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
import imgui.extension.imguiknobs.ImGuiKnobs;
import imgui.extension.imguiknobs.flag.ImGuiKnobFlags;
import imgui.extension.imguiknobs.flag.ImGuiKnobVariant;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;

public class ExampleKnobs {

    private static final ImFloat EXAMPLE_TICK_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_DOT_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_SPACE_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_WIPER_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_WIPER_DOT_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_WIPER_ONLY_KNOB_VAL = new ImFloat(0);
    private static final ImFloat EXAMPLE_STEPPED_KNOB_VAL = new ImFloat(0);

    public static void show(final ImBoolean showKnobsWindow) {
        ImGui.setNextWindowSize(200, 400, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX() + 200, ImGui.getMainViewport().getPosY() + 200, ImGuiCond.Once);
        if (ImGui.begin("Knobs Demo Window", showKnobsWindow, ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse)) {
            ImGui.textWrapped("This is a demo for dear ImGui Knobs");
            if (ImGui.beginChild("Float Knobs")) {
                ImGuiKnobs.knob("Example Tick Knob", EXAMPLE_TICK_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.Tick, 50.0f, ImGuiKnobFlags.None, 1);
                ImGuiKnobs.knob("Example Dot Knob", EXAMPLE_DOT_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.Dot, 50.0f, ImGuiKnobFlags.None, 1);
                ImGuiKnobs.knob("Example Space Knob", EXAMPLE_SPACE_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.Space, 50.0f, ImGuiKnobFlags.None, 1);
                ImGuiKnobs.knob("Example Wiper Knob", EXAMPLE_WIPER_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.Wiper, 50.0f, ImGuiKnobFlags.None, 1);
                ImGuiKnobs.knob("Example Wiper Dot Knob", EXAMPLE_WIPER_DOT_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.WiperDot, 50.0f, ImGuiKnobFlags.None, 1);
                ImGuiKnobs.knob("Example Wiper Only Knob", EXAMPLE_WIPER_ONLY_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.WiperOnly, 50.0f, ImGuiKnobFlags.None, 1);
                ImGuiKnobs.knob("Example Stepped Knob", EXAMPLE_STEPPED_KNOB_VAL, 0.0f, 10.0f, 0.2f, "%.1f", ImGuiKnobVariant.Stepped, 50.0f, ImGuiKnobFlags.None, 1);
                ImGui.endChild();
            }

            ImGui.end();
        }
    }

}
