let alertTriggered = false;

document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM fully loaded");

    // ユーザー情報を取得
    fetch('/getUserData')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(userData => {
            console.log("User data fetched:", userData);
            if (userData) {
                // index.html の更新は別の方法で行うため、ここではデータだけを localStorage に保存
                localStorage.setItem("updatedExperiencePoints", userData.experiencePoints);
                localStorage.setItem("levelImagePath", userData.levelImagePath);

                // localStorage 更新後に即座に更新を反映
                updateExperienceDisplay(userData.experiencePoints);
                updateLevelImage(userData.levelImagePath);
            } else {
                console.warn("No user data returned from server.");
            }
        })
        .catch(error => {
            console.error("Error fetching user data:", error);
            alert("ユーザー情報の更新に失敗しました。");
        });
});

// レッスン完了時の処理
function completeLesson(lessonId) {
    console.log("completeLesson called with lessonId:", lessonId);
    if (alertTriggered) return;  // すでにアラートが表示された場合は処理を終了

    const lessonIdNum = Number(lessonId);  // lessonIdを数値に変換
    const url = `/completeLesson/${lessonIdNum}`; // 正しいURLを構築
    console.log("Fetch URL:", url); // デバッグ用

    fetch(url, { method: 'POST' })
        .then(response => {
            console.log("Response status:", response.status); // レスポンスステータスを表示
            if (!response.ok) {
                return response.json().then(errData => {
                    console.error("Error response data:", errData); // エラーレスポンスの内容を表示
                    throw new Error('ネットワークエラー');
                });
            }
            return response.json();
        })
        .then(data => {
            console.log("Fetched data:", data); 
            if (data.success) {
                // レベル更新は index.html 側で行うため、必要なデータを localStorage に保存
                localStorage.setItem("updatedExperiencePoints", data.updatedExperiencePoints);
                localStorage.setItem("levelImagePath", data.levelImagePath);

                // localStorage 更新後に即座に更新を反映
                updateExperienceDisplay(data.updatedExperiencePoints);
                updateLevelImage(data.levelImagePath);

                const completedIds = data.completedContentIds;
                document.querySelectorAll('.cp_sidebar_nav li').forEach(item => {
                    const contentId = parseInt(item.dataset.id, 10);
                    if (completedIds.includes(contentId)) {
                        item.querySelector('.status').textContent = "完了";
                    }
                });

                // アラートを表示
                alert("レッスンを完了しました");
                alertTriggered = false; // アラートが表示されたことを記録
            } else {
                alert("レッスン完了に失敗しました。");
            }
        })
        .catch(error => {
            console.error("Fetch error:", error); // 詳細なエラーメッセージを表示
            alert("ネットワークエラーが発生しました。");
        });
}

// 経験値表示更新関数
function updateExperienceDisplay(experiencePoints) {
    const remainingExperience = experiencePoints % 100;
    const experienceDisplay = document.querySelector(".experience-display");
    const progressBar = document.querySelector(".progress-bar");

    if (experienceDisplay) {
        experienceDisplay.textContent = `経験値: ${remainingExperience} / 100`;
    }

    if (progressBar) {
        progressBar.style.width = `${remainingExperience}%`;
    }
}

// レベル画像更新関数
function updateLevelImage(imagePath) {
    const levelImage = document.querySelector(".level-image");
    if (levelImage) {
        levelImage.src = imagePath;
    }
}
