document.addEventListener("DOMContentLoaded", () => {
    // localStorageから経験値とレベル画像パスを取得
    const updatedExperiencePoints = localStorage.getItem("updatedExperiencePoints");
    const levelImagePath = localStorage.getItem("levelImagePath");

    // 経験値の更新処理
    if (updatedExperiencePoints !== null) {
        const experienceDisplay = document.querySelector(".experience-display");
        const progressBar = document.querySelector(".progress-bar");

        const remainingExperience = updatedExperiencePoints % 100;
        if (experienceDisplay) {
            experienceDisplay.textContent = `経験値: ${remainingExperience} / 100`;
        }

        if (progressBar) {
            // プログレスバーの幅を更新
            progressBar.style.width = `${(remainingExperience / 100) * 100}%`;
        }
    }

    // レベル画像の更新処理
    if (levelImagePath) {
        const levelImage = document.querySelector(".level-image img"); // imgタグを直接取得
        if (levelImage) {
            levelImage.src = levelImagePath;
        }
    }
});

// レベル画像を更新する関数
function updateLevelImage(newImagePath) {
    localStorage.setItem("levelImagePath", newImagePath);
    const levelImage = document.querySelector(".level-image img"); // imgタグを直接取得
    if (levelImage) {
        levelImage.src = newImagePath;
    }
}

// 経験値を更新する関数（必要なら）
function updateExperiencePoints(newExperiencePoints) {
    localStorage.setItem("updatedExperiencePoints", newExperiencePoints);
    const experienceDisplay = document.querySelector(".experience-display");
    const progressBar = document.querySelector(".progress-bar");

    const remainingExperience = newExperiencePoints % 100;
    if (experienceDisplay) {
        experienceDisplay.textContent = `経験値: ${remainingExperience} / 100`;
    }

    if (progressBar) {
        progressBar.style.width = `${(remainingExperience / 100) * 100}%`;
    }
}
