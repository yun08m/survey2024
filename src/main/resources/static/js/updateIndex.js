document.addEventListener("DOMContentLoaded", () => {
    updateUIFromLocalStorage();
    
    // 定期的に localStorage の更新をチェック
    setInterval(updateUIFromLocalStorage, 1000);  // 1秒ごとにチェック
});

function updateUIFromLocalStorage() {
    const updatedExperiencePoints = localStorage.getItem("updatedExperiencePoints");
    const levelImagePath = localStorage.getItem("levelImagePath");

    if (updatedExperiencePoints) {
        updateExperienceDisplay(updatedExperiencePoints);
    }

    if (levelImagePath) {
        updateLevelImage(levelImagePath);
    }
}

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

function updateLevelImage(imagePath) {
    const levelImage = document.querySelector(".level-image");
    if (levelImage) {
        levelImage.src = imagePath;
    }
}
