# TrainingApp – Android + GitHub + Codex Setup Notes

This project is my starting point for building an Android strength & bodybuilding training app in **Kotlin**, with the code hosted on **GitHub** and assisted by **OpenAI Codex**.

This README documents the exact steps I followed to:

1. Create the initial Android project in Android Studio.  
2. Initialize Git and push the project to GitHub using a Personal Access Token (PAT).  
3. Connect the GitHub repository to **ChatGPT Codex** so the model can see and edit the real codebase.

---

## 1. Create the Android Project (Android Studio)

1. Open **Android Studio** → **New Project**.
2. Choose the **“Empty Activity”** template.
3. Set:
   - **Name:** `TrainingApp` (or any name you prefer)
   - **Language:** `Kotlin`
   - **Minimum SDK:** `API 26` or higher
4. Click **Finish** and let Gradle sync.
5. Connect an Android phone (with USB + developer mode) **or** start an emulator.
6. Click **Run ▶** and make sure the app installs and shows a simple screen.

At this point, the base Android project is working locally.

---

## 2. Install and Configure Git (Ubuntu)

Install Git:

```bash
sudo apt update
sudo apt install git
```

Configure your Git identity:

```bash
git config --global user.name "your-name"
git config --global user.email "your-email@example.com"
```

Make sure you already have a **GitHub account** (create one if needed).

---

## 3. Initialize Git in the Android Project

From the terminal:

```bash
cd ~/AndroidStudioProjects/TrainingApp   # adjust the path if different

git init
```

Create a `.gitignore` file in the project root to avoid committing build artifacts:

```bash
cat > .gitignore << 'EOF'
*.iml
.gradle
/local.properties
/.idea/caches
/.idea/libraries
/.idea/workspace.xml
/.idea/navEditor.xml
/.idea/assetWizardSettings.xml
.DS_Store
/build
/captures
.externalNativeBuild
.cxx
EOF
```

Stage and commit the initial project:

```bash
git add .
git commit -m "Initial commit: empty Android project"
```

Now the local repo is ready.

---

## 4. Create the GitHub Repository

On GitHub:

1. Click **New repository**.
2. Choose a name, e.g. `training-app` or `android-strength-coach`.
3. **Important:** select **NO README**, **NO .gitignore**, **NO license**  
   (we already created `.gitignore` locally to avoid merge conflicts).
4. Create the repo.

GitHub will show you the repo URL, for example:

- SSH: `git@github.com:YOUR_USER/training-app.git`  
- HTTPS: `https://github.com/YOUR_USER/training-app.git`

---

## 5. Connect Local Repo to GitHub & Push (with PAT)

Back in the project folder:

```bash
cd ~/AndroidStudioProjects/TrainingApp   # if not already there

# Use ONE of these, depending on SSH or HTTPS:

git remote add origin git@github.com:YOUR_USER/training-app.git
# or
# git remote add origin https://github.com/YOUR_USER/training-app.git

git branch -M main
```

### 5.1 Create a Personal Access Token (PAT) on GitHub

1. Go to GitHub and log in.
2. Top-right avatar → **Settings**.
3. Left menu (bottom) → **Developer settings**.
4. **Personal access tokens**:
   - Either **Fine-grained token (recommended)**, or  
   - **Tokens (classic)** → **Generate new token**.
5. Give it a name, e.g. `training-app-token`.
6. Set an expiration (e.g. 90 days).
7. Permissions:
   - For a **fine-grained token**: select your repo or **All repositories** and enable **Repository → Read and write**.
   - For a **classic token**: enable **`repo`**.
8. Click **Generate token**.
9. Copy the token somewhere safe (you’ll only see it once), e.g.:  
   `ghp_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`.

### 5.2 Push Using Username + Token

From the project folder:

```bash
cd ~/AndroidStudioProjects/TrainingApp   # or your path
git push -u origin main
```

When prompted:

- **Username for `https://github.com`:**  
  → enter your **GitHub username** (e.g. `ot3hn00b`), **not** your email.

- **Password for `https://github.com`:**  
  → paste the **token** you just created (on many terminals: `Ctrl+Shift+V`).

Hit **Enter**.  
If everything is correct, the branch `main` is now pushed to GitHub.

### 5.3 (Optional) Remember Credentials

To avoid retyping the token each time:

```bash
git config --global credential.helper store
```

Next time you `git push`, Git will reuse the stored token.

---

## 6. Connect ChatGPT Codex to the GitHub Repository

To let Codex see and edit this repo:

1. Go to **https://chatgpt.com/codex**.
2. Click **“Connect to GitHub”** and authorize the connection.

Then complete the setup in ChatGPT:

1. Open ChatGPT (normal UI, not `/codex`).
2. Click your avatar → **Settings**.
3. Go to **Apps & connectors → GitHub** (or **Connections → GitHub**, depending on UI).
4. You should see that GitHub is connected.
5. Click the small **gear / manage / configure** link next to GitHub.
6. On the configuration page, choose:
   - **All repositories**, **or**
   - **Only selected repositories** and explicitly tick your Android repo.

> Note: Private or newly created repos often don’t appear in Codex until this configuration step is done.

After this, Codex can:

- Read the current project files.
- Propose changes based on the actual Kotlin/Android code.
- Depending on the setup, create branches, commits, or PRs directly in the repo.

---

## Next Steps

With the environment ready (Android Studio, Git, GitHub, PAT, and Codex integration), the next phases will be:

- Designing the app’s core screens (Main → Workout → Exercise).
- Using Codex tasks to implement features.
- Iterating on training logic (strength/hypertrophy programs, timers, tracking, etc.).

This README captures the initial setup so I can reproduce it or extend it later.
