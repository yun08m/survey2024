function completeLesson(lessonId) {
    const lessonIdNum = Number(lessonId);  // lessonIdを数値に変換
    const url = `/completeLesson/${lessonIdNum}`; // 正しいURLを構築
    console.log("Fetch URL:", url); // デバッグ用

    fetch(url, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                throw new Error('ネットワークエラー');
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                parent.updateExperiencePoints(data.updatedExperiencePoints);
            } else {
                alert("レッスン完了に失敗しました。");
            }
        })
        .catch(error => {
            console.error(error);
            alert("ネットワークエラーが発生しました。");
        });
}
