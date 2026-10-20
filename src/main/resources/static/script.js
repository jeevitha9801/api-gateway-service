document.addEventListener("DOMContentLoaded", () => {

    loadDashboard();

    document
        .getElementById("sendBtn")
        .addEventListener("click", sendRequest);
});

async function sendRequest() {

    try {

        const response = await fetch(
            "/api/demo",
            {
                headers: {
                    "X-API-KEY": "test123"
                }
            }
        );

        const text = await response.text();

        if (response.ok) {

            showToast(
                response.status + " - " + text,
                "success"
            );

        } else {

            showToast(
                response.status + " - " + text,
                "error"
            );
        }

        await loadDashboard();

    } catch (error) {

        showToast(
            "Unable to connect to API",
            "error"
        );
    }
}

async function loadDashboard() {

    try {

        const response = await fetch("/dashboard");

        const data = await response.json();

        document.getElementById("totalRequests")
            .innerText = data.totalRequests;

        document.getElementById("blockedRequests")
            .innerText = data.blockedRequests;

        document.getElementById("avgResponseTime")
            .innerText =
            data.averageResponseTime + " ms";

        document.getElementById("rateLimit")
            .innerText =
            data.rateLimitPerMinute + "/min";

        const statusElement =
            document.getElementById("status");

        statusElement.classList.remove(
            "status-healthy",
            "status-warning"
        );

        if (data.status === "Healthy") {

            statusElement.innerText = "Healthy";

            statusElement.classList.add(
                "status-healthy"
            );

        } else {

            statusElement.innerText = "Warning";

            statusElement.classList.add(
                "status-warning"
            );
        }

    } catch (error) {

        console.error(
            "Dashboard load failed",
            error
        );
    }
}

let toastTimer;

function showToast(message, type) {

    const toast =
        document.getElementById("toast");

    const toastMessage =
        document.getElementById("toastMessage");

    toastMessage.innerText = message;

    toast.className = "toast";

    toast.classList.add(type);
    toast.classList.add("show");

    clearTimeout(toastTimer);

    toastTimer = setTimeout(() => {
        toast.classList.remove("show");
    }, 3000);
}