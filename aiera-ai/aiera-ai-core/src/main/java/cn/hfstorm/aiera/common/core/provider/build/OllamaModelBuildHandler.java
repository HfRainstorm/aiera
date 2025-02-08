/*
 * Copyright (c) 2024 LangChat. TyCoding All Rights Reserved.
 *
 * Licensed under the GNU Affero General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.hfstorm.aiera.common.core.provider.build;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

/**
 * @author GB
 * @since 2024-08-19 10:08
 */
@Slf4j
@Component
public class OllamaModelBuildHandler implements ModelBuildHandler {

    @Override
    public boolean whetherCurrentModel(ChatModel model) {
        return false;
    }

    @Override
    public ChatModel buildStreamingChat(ChatModel model) {
        return null;
    }
}
