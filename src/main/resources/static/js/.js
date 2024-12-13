document.addEventListener("DOMContentLoaded", () => {
    const updatedExperiencePoints = localStorage.getItem("updatedExperiencePoints");
    const levelImagePath = localStorage.getItem("levelImagePath");

    if (updatedExperiencePoints) {
        // 経験値の更新
        const experienceDisplay = document.querySelector(".experience-display");
        const progressBar = document.querySelector(".progress-bar");

        const remainingExperience = updatedExperiencePoints % 100;
        if (experienceDisplay) {
            experienceDisplay.textContent = `経験値: ${remainingExperience} / 100`;
        }

        if (progressBar) {
            progressBar.style.width = `${remainingExperience}%`;
        }
    }

    if (levelImagePath) {
        // レベル画像の更新
        const levelImage = document.querySelector(".level-image");
        if (levelImage) {
            levelImage.src = levelImagePath;
        }
    }
});
